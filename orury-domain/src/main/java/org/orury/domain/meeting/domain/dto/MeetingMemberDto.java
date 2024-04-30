package org.orury.domain.meeting.domain.dto;

import org.orury.domain.meeting.domain.entity.MeetingMember;
import org.orury.domain.meeting.domain.entity.MeetingMemberPK;

/**
 * DTO for {@link MeetingMember}
 */
public record MeetingMemberDto(
        MeetingMemberPK meetingMemberPK,
        Boolean meetingViewed
) {
    public static MeetingMemberDto of(MeetingMemberPK meetingMemberPK, Boolean meetingViewed) {
        return new MeetingMemberDto(meetingMemberPK, meetingViewed);
    }

    public static MeetingMemberDto from(MeetingMember meetingMember) {
        return MeetingMemberDto.of(meetingMember.getMeetingMemberPK(), meetingMember.getMeetingViewed());
    }

    public MeetingMember toEntity() {
        return MeetingMember.of(meetingMemberPK, meetingViewed);
    }
}
