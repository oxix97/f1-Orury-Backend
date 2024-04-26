package org.orury.client.review.interfaces.request;

import org.orury.domain.global.validation.EnumValue;
import org.orury.domain.gym.domain.dto.GymDto;
import org.orury.domain.review.domain.dto.Difficulty;
import org.orury.domain.review.domain.dto.ReviewDto;
import org.orury.domain.user.domain.dto.UserDto;

import java.util.List;

public record ReviewCreateRequest(
        String content,
        float score,
        Long gymId,
        String description,

        @EnumValue(enumClass = Difficulty.class, message = "유효하지 않은 난이도입니다.")
        Difficulty difficulty
) {
    public static ReviewCreateRequest of(String content, float score, Long gymId, String description, Difficulty difficulty) {
        return new ReviewCreateRequest(content, score, gymId, description, difficulty);
    }

    public ReviewDto toDto(UserDto userDto, GymDto gymDto) {
        return ReviewDto.of(
                null,
                content,
                List.of(),
                score,
                0,
                0,
                0,
                0,
                userDto,
                gymDto,
                null,
                null,
                description,
                difficulty
        );
    }
}
