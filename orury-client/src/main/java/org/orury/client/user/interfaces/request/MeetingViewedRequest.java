package org.orury.client.user.interfaces.request;

import org.orury.domain.crew.domain.dto.CrewMemberDto;
import org.orury.domain.crew.domain.entity.CrewMemberPK;

public record MeetingViewedRequest(
        Long crewId,
        boolean meetingViewed
) {
    public static MeetingViewedRequest of(Long crewId, boolean meetingViewed) {
        return new MeetingViewedRequest(crewId, meetingViewed);
    }

    public CrewMemberDto toDto(CrewMemberPK crewMemberPK) {
        return CrewMemberDto.of(crewMemberPK, meetingViewed, null, null);
    }
}
