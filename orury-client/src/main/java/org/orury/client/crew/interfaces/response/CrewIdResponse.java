package org.orury.client.crew.interfaces.response;

public record CrewIdResponse(
        Long crewId
) {
    public static CrewIdResponse of(Long id) {
        return new CrewIdResponse(
                id
        );
    }
}
