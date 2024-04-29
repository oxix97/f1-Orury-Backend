package org.orury.domain.review.domain.dto;

import org.orury.domain.gym.domain.dto.GymDto;
import org.orury.domain.review.domain.entity.Review;
import org.orury.domain.user.domain.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link Review}
 */
public record ReviewDto(
        Long id,
        String content,
        List<String> images,
        float score,
        int wantToGoCount,
        int helpedCount,
        int greatCount,
        int funnyCount,
        UserDto userDto,
        GymDto gymDto,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String description,
        Difficulty difficulty
) {
    public static ReviewDto of(
            Long id,
            String content,
            List<String> images,
            float score,
            int wantToGoCount,
            int helpedCount,
            int greatCount,
            int funnyCount,
            UserDto userDto,
            GymDto gymDto,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String description,
            Difficulty difficulty
    ) {
        return new ReviewDto(
                id,
                content,
                images,
                score,
                wantToGoCount,
                helpedCount,
                greatCount,
                funnyCount,
                userDto,
                gymDto,
                createdAt,
                updatedAt,
                description,
                difficulty
        );
    }

    public static ReviewDto from(Review entity) {
        return ReviewDto.of(
                entity.getId(),
                entity.getContent(),
                entity.getImages(),
                entity.getScore(),
                entity.getWantToGoCount(),
                entity.getHelpedCount(),
                entity.getGreatCount(),
                entity.getFunnyCount(),
                UserDto.from(entity.getUser()),
                GymDto.from(entity.getGym()),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDescription(),
                entity.getDifficulty()
        );
    }

    public static ReviewDto from(Review entity, List<String> imgUrls, String profileImgUrl) {
        return ReviewDto.of(
                entity.getId(),
                entity.getContent(),
                imgUrls,
                entity.getScore(),
                entity.getWantToGoCount(),
                entity.getHelpedCount(),
                entity.getGreatCount(),
                entity.getFunnyCount(),
                UserDto.from(entity.getUser(), profileImgUrl),
                GymDto.from(entity.getGym()),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDescription(),
                entity.getDifficulty()
        );
    }

    public Review toEntity() {
        return Review.of(
                id,
                content,
                images,
                score,
                wantToGoCount,
                helpedCount,
                greatCount,
                funnyCount,
                userDto.toEntity(),
                gymDto.toEntity(),
                createdAt,
                updatedAt,
                description,
                difficulty
        );
    }

    public Review toEntity(List<String> images) {
        return Review.of(
                id,
                content,
                images,
                score,
                wantToGoCount,
                helpedCount,
                greatCount,
                funnyCount,
                userDto.toEntity(),
                gymDto.toEntity(),
                createdAt,
                updatedAt,
                description,
                difficulty
        );
    }
}
