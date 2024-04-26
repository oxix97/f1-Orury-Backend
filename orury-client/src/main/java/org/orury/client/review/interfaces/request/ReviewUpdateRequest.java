package org.orury.client.review.interfaces.request;

import org.orury.domain.global.validation.EnumValue;
import org.orury.domain.review.domain.dto.Difficulty;
import org.orury.domain.review.domain.dto.ReviewDto;

public record ReviewUpdateRequest(
        String content,
        float score,
        String description,

        @EnumValue(enumClass = Difficulty.class, message = "유효하지 않은 난이도입니다.")
        Difficulty difficulty
) {
    public static ReviewUpdateRequest of(
            String content,
            float score,
            String description,
            Difficulty difficulty
    ) {
        return new ReviewUpdateRequest(
                content,
                score,
                description,
                difficulty
        );
    }

    public ReviewDto toDto(ReviewDto reviewDto) {

        return ReviewDto.of(
                reviewDto.id(),
                content,
                reviewDto.images(),
                score,
                reviewDto.interestCount(),
                reviewDto.likeCount(),
                reviewDto.helpCount(),
                reviewDto.thumbCount(),
                reviewDto.angryCount(),
                reviewDto.userDto(),
                reviewDto.gymDto(),
                reviewDto.createdAt(),
                null,
                reviewDto.description(),
                reviewDto.difficulty()
        );
    }

}
