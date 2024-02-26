package org.orury.client.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.orury.common.error.code.UserErrorCode;
import org.orury.common.error.exception.BusinessException;
import org.orury.common.util.ImageUrlConverter;
import org.orury.common.util.ImageUtils;
import org.orury.common.util.S3Folder;
import org.orury.domain.comment.db.repository.CommentLikeRepository;
import org.orury.domain.comment.db.repository.CommentRepository;
import org.orury.domain.global.constants.NumberConstants;
import org.orury.domain.gym.db.repository.GymLikeRepository;
import org.orury.domain.gym.db.repository.GymRepository;
import org.orury.domain.post.db.repository.PostLikeRepository;
import org.orury.domain.post.db.repository.PostRepository;
import org.orury.domain.review.db.repository.ReviewReactionRepository;
import org.orury.domain.review.db.repository.ReviewRepository;
import org.orury.domain.user.db.model.User;
import org.orury.domain.user.db.repository.UserRepository;
import org.orury.domain.user.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ImageUtils imageUtils;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewReactionRepository reviewReactionRepository;
    private final GymRepository gymRepository;
    private final GymLikeRepository gymLikeRepository;

    public UserDto getUserDtoById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND));
        String profileUrl = imageUtils.getUserImageUrl(user.getProfileImage());
        return UserDto.from(user, profileUrl);
    }

    @Transactional
    public void updateProfileImage(UserDto userDto, MultipartFile image) throws BusinessException {
        //기존에 저장된 요소 삭제
        imageUtils.oldS3ImagesDelete(S3Folder.USER.getName(), userDto.profileImage());

        imageUploadAndSave(userDto, image);
    }

    @Transactional
    public void updateUserInfo(UserDto userDto) {
        userRepository.save(userDto.toEntity(ImageUrlConverter.splitUrlToImage(userDto.profileImage())));
    }

    @Transactional
    public void deleteUser(UserDto userDto) {
        deleteReviewReactionsByUserId(userDto.id());
        deleteGymLikesByUserId(userDto.id());
        deleteCommentLikesByUserId(userDto.id());
        deletePostLikesByUserId(userDto.id());
        deletePostsByUserId(userDto.id());

        imageUtils.oldS3ImagesDelete(S3Folder.USER.getName(), userDto.profileImage());

        User user = User.of(
                userDto.id(),
                userDto.email(),
                userDto.nickname(),
                userDto.password(),
                userDto.signUpType(),
                userDto.gender(),
                userDto.birthday(),
                imageUtils.getUserDefaultImage(),
                userDto.createdAt(),
                null,
                NumberConstants.IS_DELETED
        );

        userRepository.save(user);
    }

    private void deleteReviewReactionsByUserId(Long userId) {
        reviewReactionRepository.findByReviewReactionPK_UserId(userId)
                .forEach(
                        reviewReaction -> {
                            reviewRepository.decreaseReactionCount(reviewReaction.getReviewReactionPK()
                                    .getReviewId(), reviewReaction.getReactionType());
                            reviewReactionRepository.delete(reviewReaction);
                        }
                );
    }

    private void deleteGymLikesByUserId(Long userId) {
        gymLikeRepository.findByGymLikePK_UserId(userId)
                .forEach(
                        gymLike -> {
                            gymRepository.decreaseLikeCount(gymLike.getGymLikePK()
                                    .getGymId());
                            gymLikeRepository.delete(gymLike);
                        }
                );
    }

    private void deleteCommentLikesByUserId(Long userId) {
        commentLikeRepository.findByCommentLikePK_UserId(userId)
                .forEach(
                        commentLike -> {
                            commentRepository.decreaseLikeCount(commentLike.getCommentLikePK()
                                    .getCommentId());
                            commentLikeRepository.delete(commentLike);
                        }
                );
    }

    private void deletePostLikesByUserId(Long userId) {
        postLikeRepository.findByPostLikePK_UserId(userId)
                .forEach(
                        postLike -> {
                            postRepository.decreaseCommentCount(postLike.getPostLikePK()
                                    .getPostId());
                            postLikeRepository.delete(postLike);
                        }
                );
    }

    private void deletePostsByUserId(Long userId) {
        postRepository.findByUserId(userId)
                .forEach(
                        post -> {
                            imageUtils.oldS3ImagesDelete(S3Folder.POST.getName(), post.getImages());
                            postRepository.delete(post);
                        }
                );
    }

    private void imageUploadAndSave(UserDto userDto, MultipartFile file) {
        //MultipartFile이 오지 않은 경우 -> 유저의 프로필 이미지를 기본 이미지로 변경
        if (file == null || file.isEmpty()) {
            userRepository.save(userDto.toEntity(imageUtils.getUserDefaultImage()));
        } else {
            //MultipartFile이 오는 경우 -> 유저의 프로필 이미지를 업로드한 이미지로 변경
            String image = imageUtils.upload(S3Folder.USER.getName(), file);
            userRepository.save(userDto.toEntity(image));
        }
    }
}