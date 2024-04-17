package org.orury.client.crew.interfaces.response;

import org.orury.domain.crew.domain.dto.CrewApplicationDto;

public record CrewApplicantsResponse(
        Long id,
        String nickname,
        String profileImage,
        String answer
) {
    public static CrewApplicantsResponse of(
            Long id,
            String nickname,
            String profileImage,
            String answer
    ) {
        return new CrewApplicantsResponse(
                id,
                nickname,
                profileImage,
                answer
        );
    }

    public static CrewApplicantsResponse of(CrewApplicationDto crewApplicationDto) {
        return CrewApplicantsResponse.of(
                crewApplicationDto.userDto().id(),
                crewApplicationDto.userDto().nickname(),
                crewApplicationDto.userDto().profileImage(),
                crewApplicationDto.answer()
        );
    }
}
