package org.orury.domain.crew.domain.dto;

import org.orury.domain.crew.domain.entity.CrewMember;
import org.orury.domain.crew.domain.entity.CrewMemberPK;

import java.time.LocalDateTime;

/**
 * DTO for {@link CrewMember}
 */
public record CrewMemberDto(
        CrewMemberPK crewMemberPK,
        Boolean meetingViewed,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CrewMemberDto of(
            CrewMemberPK crewMemberPK,
            Boolean meetingViewed,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new CrewMemberDto(
                crewMemberPK,
                meetingViewed,
                createdAt,
                updatedAt
        );
    }

    public static CrewMemberDto from(CrewMember crewMember) {
        return CrewMemberDto.of(
                crewMember.getCrewMemberPK(),
                crewMember.getMeetingViewed(),
                crewMember.getCreatedAt(),
                crewMember.getUpdatedAt()
        );
    }

    public CrewMember toEntity() {
        return CrewMember.of(
                crewMemberPK,
                meetingViewed,
                createdAt,
                updatedAt
        );
    }
}
