package org.orury.client.crew.interfaces.response;

import org.orury.domain.user.domain.dto.UserDto;

public record CrewApplicantsResponse(
        Long id,
        String nickname,
        String profileImage
) {
    public static CrewApplicantsResponse of(
            Long id,
            String nickname,
            String profileImage
    ) {
        return new CrewApplicantsResponse(
                id,
                nickname,
                profileImage
        );
    }

    public static CrewApplicantsResponse of(UserDto userDto) {
        return CrewApplicantsResponse.of(
                userDto.id(),
                userDto.nickname(),
                userDto.profileImage()
        );
    }
}
