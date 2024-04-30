package org.orury.domain.meeting.domain.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.NumericBooleanConverter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Slf4j
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"meetingMemberPK"}, callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "crew_meeting_member")
public class MeetingMember {
    @EmbeddedId
    private MeetingMemberPK meetingMemberPK;

    @Convert(converter = NumericBooleanConverter.class)
    private Boolean meetingViewed;

    private MeetingMember(MeetingMemberPK meetingMemberPK, Boolean meetingViewed) {
        this.meetingMemberPK = meetingMemberPK;
        this.meetingViewed = meetingViewed;
    }

    public static MeetingMember of(MeetingMemberPK meetingMemberPK, Boolean meetingViewed) {
        return new MeetingMember(meetingMemberPK, meetingViewed);
    }
}
