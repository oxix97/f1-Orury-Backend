package org.orury.domain.crew.domain.dto;

import org.orury.domain.crew.domain.entity.CrewApplication;
import org.orury.domain.crew.domain.entity.CrewApplicationPK;
import org.orury.domain.user.domain.dto.UserDto;

import java.time.LocalDateTime;

/**
 * DTO for {@link CrewApplication}
 */
public record CrewApplicationDto(
        Long crewId,
        UserDto userDto,
        String answer,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CrewApplicationDto of(
            Long crewId,
            UserDto userDto,
            String answer,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new CrewApplicationDto(
                crewId,
                userDto,
                answer,
                createdAt,
                updatedAt
        );
    }

    public static CrewApplicationDto from(CrewApplication crewApplication, UserDto userDto) {
        return CrewApplicationDto.of(
                crewApplication.getCrewApplicationPK().getCrewId(),
                userDto,
                crewApplication.getAnswer(),
                crewApplication.getCreatedAt(),
                crewApplication.getUpdatedAt()
        );
    }

    public CrewApplication toEntity() {
        return CrewApplication.of(
                CrewApplicationPK.of(userDto.id(), crewId),
                answer,
                createdAt,
                updatedAt
        );
    }

    public static CrewApplicationDto of(CrewDto crewDto, UserDto userDto, String answer) {
        return CrewApplicationDto.of(
                crewDto.id(),
                userDto,
                answer,
                null,
                null
        );
    }
}
