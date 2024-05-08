package org.orury.client.user.interfaces.response;

import org.orury.domain.crew.domain.dto.CrewDto;

public record MyCrewMemberResponse(
        Long crewId,
        String crewName,
        Boolean meetingViewed
) {
    public static MyCrewMemberResponse of(
            Long crewId,
            String crewName,
            Boolean meetingViewed
    ) {
        return new MyCrewMemberResponse(
                crewId,
                crewName,
                meetingViewed
        );
    }

    public static MyCrewMemberResponse of(CrewDto crewDto, Boolean meetingViewed) {
        return MyCrewMemberResponse.of(
                crewDto.id(),
                crewDto.name(),
                meetingViewed
        );
    }
}
