package org.orury.client.crew.interfaces.request;

import org.apache.logging.log4j.util.Strings;
import org.hibernate.validator.constraints.Length;
import org.orury.domain.crew.domain.dto.CrewDto;
import org.orury.domain.crew.domain.dto.CrewGender;
import org.orury.domain.crew.domain.dto.CrewStatus;
import org.orury.domain.global.domain.Region;
import org.orury.domain.global.validation.EnumValue;
import org.orury.domain.global.validation.EnumValues;
import org.orury.domain.user.domain.dto.UserDto;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CrewRequest(
        @Length(min = 3, max = 15, message = "크루명은 3~15 글자수로 설정 가능합니다.")
        String name,

        @Min(value = 3, message = "크루 최소인원은 3명입니다.") @Max(value = 100, message = "크루 최대인원은 100명입니다.")
        int capacity,

        @Size(min = 1, max = 3, message = "활동 지역은 최소 1개, 최대 3개까지만 추가할 수 있습니다.")
        @EnumValues(enumClass = Region.class, message = "유효하지 않은 지역이 포함되어 있습니다.")
        List<Region> regions,

        @NotEmpty(message = "크루 소개는 필수 입력사항입니다.")
        String description,

        @Min(value = 12, message = "최저 연령을 12세 이상으로 설정해 주세요.")
        int minAge,

        @Max(value = 100, message = "최고 연령을 100세 이하로 설정해 주세요.")
        int maxAge,

        @EnumValue(enumClass = CrewGender.class, message = "성별은 ANY, FEMALE, MALE 중 하나여야 합니다.")
        CrewGender gender,

        boolean permissionRequired,

        String question,

        boolean answerRequired,

        @Size(min = 1, max = 3, message = "태그는 최소 1개, 최대 3개까지만 추가할 수 있습니다.")
        List<@Size(min = 1, max = 6, message = "태그 길이는 공백 포함 6글자 이내여야 합니다.") String> tags
) {
    public CrewRequest {
        if (!permissionRequired) answerRequired = false; // permissionRequired가 false인 경우 answerRequired도 false로 설정
    }

    public static CrewRequest of(
            String name,
            int capacity,
            List<Region> regions,
            String description,
            int minAge,
            int maxAge,
            CrewGender gender,
            boolean permissionRequired,
            String question,
            boolean answerRequired,
            List<String> tags
    ) {
        return new CrewRequest(
                name,
                capacity,
                regions,
                description,
                minAge,
                maxAge,
                gender,
                permissionRequired,
                question,
                answerRequired,
                tags
        );
    }

    public CrewDto toDto(UserDto userDto) {
        return CrewDto.of(
                null,
                name,
                0,
                capacity,
                regions,
                description,
                Strings.EMPTY,
                CrewStatus.ACTIVATED,
                userDto,
                null,
                null,
                minAge,
                maxAge,
                gender,
                permissionRequired,
                question,
                answerRequired,
                tags
        );
    }

    public CrewDto toDto(CrewDto crewDto) {
        return CrewDto.of(
                crewDto.id(),
                name,
                crewDto.memberCount(),
                capacity,
                regions,
                description,
                crewDto.icon(),
                crewDto.status(),
                crewDto.userDto(),
                crewDto.createdAt(),
                null,
                minAge,
                maxAge,
                gender,
                permissionRequired,
                question,
                answerRequired,
                tags
        );
    }
}

