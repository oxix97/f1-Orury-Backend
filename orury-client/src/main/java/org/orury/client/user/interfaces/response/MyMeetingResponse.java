package org.orury.client.user.interfaces.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.orury.domain.meeting.domain.dto.MeetingDto;

import java.time.LocalDateTime;
import java.util.Objects;

public record MyMeetingResponse(
        Long crewId,
        String crewName,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        LocalDateTime startTime,
        Long gymId,
        String gymName,
        int memberCount,
        int capacity,
        boolean isCreator
) {
    public static MyMeetingResponse of(
            Long crewId,
            String crewName,
            LocalDateTime startTime,
            Long gymId,
            String gymName,
            int memberCount,
            int capacity,
            boolean isCreator
    ) {
        return new MyMeetingResponse(
                crewId,
                crewName,
                startTime,
                gymId,
                gymName,
                memberCount,
                capacity,
                isCreator
        );
    }

    public static MyMeetingResponse of(MeetingDto meetingDto, Long userId) {
        return new MyMeetingResponse(
                meetingDto.crewDto().id(),
                meetingDto.crewDto().name(),
                meetingDto.startTime(),
                meetingDto.gymDto().id(),
                meetingDto.gymDto().name(),
                meetingDto.memberCount(),
                meetingDto.capacity(),
                Objects.equals(userId, meetingDto.userDto().id())
        );
    }
}
