package org.orury.domain.meeting.domain;

import org.orury.domain.meeting.domain.entity.MeetingMember;
import org.orury.domain.user.domain.entity.User;

import java.util.List;

public interface MeetingMemberReader {
    boolean existsByMeetingIdAndUserId(Long meetingId, Long userId);

    List<MeetingMember> getOtherMeetingMembersByMeetingIdMaximum(Long meetingId, Long meetingCreatorId, int maximum);

    List<User> getMeetingMembersByMeetingId(Long meetingId);
}
