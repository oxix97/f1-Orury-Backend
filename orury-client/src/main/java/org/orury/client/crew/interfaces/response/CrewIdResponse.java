package org.orury.client.crew.interfaces.response;

import org.orury.domain.crew.domain.dto.CrewDto;

public record CrewIdResponse(
        Long id
) {
    public static CrewIdResponse of(CrewDto crewDto) {
        return new CrewIdResponse(
                crewDto.id()
        );
    }
}
