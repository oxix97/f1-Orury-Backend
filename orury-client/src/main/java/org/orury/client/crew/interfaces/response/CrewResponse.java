package org.orury.client.crew.interfaces.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.orury.domain.crew.domain.dto.CrewDto;
import org.orury.domain.crew.domain.dto.CrewStatus;
import org.orury.domain.global.domain.Region;

import java.time.LocalDateTime;
import java.util.List;

public record CrewResponse(
        Long id,
        String name,
        String headName,
        int memberCount,
        int capacity,
        int minAge,
        int maxAge,
        String gender,
        List<Region> regions,
        String description,
        String icon,
        CrewStatus status,
        String headProfileImage,
        String question,
        boolean permissionRequired,
        boolean answerRequired,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        LocalDateTime updatedAt,
        List<String> tags,
        boolean isMember,
        boolean isCrewCreator,
        List<String> userImages
) {
    public static CrewResponse of(CrewDto crewDto, boolean isMember, List<String> userImages, Long userId) {
        return new CrewResponse(
                crewDto.id(),
                crewDto.name(),
                crewDto.userDto().nickname(),
                crewDto.memberCount(),
                crewDto.capacity(),
                crewDto.minAge(),
                crewDto.maxAge(),
                crewDto.gender().name(),
                crewDto.regions(),
                crewDto.description(),
                crewDto.icon(),
                crewDto.status(),
                crewDto.userDto().profileImage(),
                crewDto.question(),
                crewDto.permissionRequired(),
                crewDto.answerRequired(),
                crewDto.createdAt(),
                crewDto.updatedAt(),
                crewDto.tags(),
                isMember,
                crewDto.userDto().id().equals(userId),
                userImages
        );
    }
}
