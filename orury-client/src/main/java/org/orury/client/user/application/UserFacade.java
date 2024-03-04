package org.orury.client.user.application;


import lombok.RequiredArgsConstructor;
import org.orury.client.global.WithCursorResponse;
import org.orury.client.post.application.PostService;
import org.orury.client.user.interfaces.request.UserInfoRequest;
import org.orury.client.user.interfaces.response.MyPostResponse;
import org.orury.domain.global.constants.NumberConstants;
import org.orury.domain.post.domain.dto.PostDto;
import org.orury.domain.user.domain.UserService;
import org.orury.domain.user.domain.dto.UserDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final PostService postService;

    public UserDto readMypage(Long id) {
        return userService.getUserDtoById(id);
    }

    public void updateProfileImage(Long id, MultipartFile image) {
        UserDto userDto = userService.getUserDtoById(id);
        userService.updateProfileImage(userDto, image);
    }

    public void updateUserInfo(Long id, UserInfoRequest userInfoRequest) {
        UserDto userDto = userService.getUserDtoById(id);
        UserDto updateUserDto = UserInfoRequest.toDto(userDto, userInfoRequest);
        userService.updateUserInfo(updateUserDto);
    }

    public WithCursorResponse<MyPostResponse> getPostsByUserId(Long id, Long cursor) {
        List<PostDto> postDtos = postService.getPostDtosByUserId(id, cursor, PageRequest.of(0, NumberConstants.POST_PAGINATION_SIZE));

        List<MyPostResponse> myPostResponses = postDtos.stream()
                .map(MyPostResponse::of)
                .toList();

        WithCursorResponse<MyPostResponse> cursorResponse = WithCursorResponse.of(myPostResponses, cursor);

        return cursorResponse;
    }

    public void deleteUser(Long id) {
        UserDto userDto = userService.getUserDtoById(id);
        userService.deleteUser(userDto);
    }
}
