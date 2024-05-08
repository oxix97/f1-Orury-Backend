package org.orury.domain.meeting.infrastructure;

import lombok.RequiredArgsConstructor;
import org.orury.domain.meeting.domain.MeetingMemberStore;
import org.orury.domain.meeting.domain.entity.Meeting;
import org.orury.domain.meeting.domain.entity.MeetingMember;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MeetingMemberStoreImpl implements MeetingMemberStore {
    private final MeetingMemberRepository meetingMemberRepository;
    private final MeetingRepository meetingRepository;

    @Override
    public void addMember(MeetingMember meetingMember) {
        meetingMemberRepository.save(meetingMember);
        meetingRepository.increaseMemberCount(meetingMember.getMeetingMemberPK().getMeetingId());
    }

    @Override
    public void removeMember(Long userId, Long meetingId) {
        meetingMemberRepository.deleteByMeetingMemberPK_UserIdAndMeetingMemberPK_MeetingId(userId, meetingId);
        meetingRepository.decreaseMemberCount(meetingId);
    }

    @Override
    public void removeAllByUserIdAndCrewId(Long userId, Long crewId) {
        List<Meeting> meetings = meetingRepository.findAllByCrew_Id(crewId);
        meetings.forEach(meeting -> meetingMemberRepository.findByMeetingMemberPK_MeetingIdAndMeetingMemberPK_UserId(meeting.getId(), userId)
                .ifPresent(meetingMember -> {
                    meetingMemberRepository.delete(meetingMember);
                    meetingRepository.decreaseMemberCount(meeting.getId());
                })
        );
    }
}
