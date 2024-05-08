package org.orury.domain.meeting.infrastructure;

import lombok.RequiredArgsConstructor;
import org.orury.domain.crew.infrastructures.CrewMemberRepository;
import org.orury.domain.meeting.domain.MeetingReader;
import org.orury.domain.meeting.domain.entity.Meeting;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MeetingReaderImpl implements MeetingReader {
    private final MeetingRepository meetingRepository;
    private final MeetingMemberRepository meetingMemberRepository;
    private final CrewMemberRepository crewMemberRepository;

    @Override
    public Optional<Meeting> findById(Long meetingId) {
        return meetingRepository.findById(meetingId);
    }

    @Override
    public List<Meeting> getNotStartedMeetingsByCrewId(Long crewId) {
        return meetingRepository.findByCrew_IdAndStartTimeAfterOrderByIdDesc(crewId, LocalDateTime.now());
    }

    @Override
    public List<Meeting> getStartedMeetingsByCrewId(Long crewId) {
        return meetingRepository.findByCrew_IdAndStartTimeBeforeOrderByIdDesc(crewId, LocalDateTime.now());
    }

    @Override
    public List<Meeting> getUpcomingMeetingsByUserId(Long userId) {
        List<Meeting> upcomingMeetings = new LinkedList<>();
        crewMemberRepository.findByCrewMemberPK_UserIdAndMeetingViewedTrue(userId).stream()
                .map(crewMember -> crewMember.getCrewMemberPK().getCrewId())
                .map(crewId -> meetingRepository.findByCrew_IdAndStartTimeAfterOrderByIdDesc(crewId, LocalDateTime.now()))
                .map(meetings -> meetings.stream()
                        .filter(meeting -> meetingMemberRepository.existsByMeetingMemberPK_MeetingIdAndMeetingMemberPK_UserId(meeting.getId(), userId))
                ).forEach(meetings -> upcomingMeetings.addAll(meetings.toList()));
        return upcomingMeetings;
    }
}
