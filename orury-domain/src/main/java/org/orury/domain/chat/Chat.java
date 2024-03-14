package org.orury.domain.chat;

import jakarta.persistence.Convert;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.orury.domain.global.listener.UserProfileConverter;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long crewId;

    private Long senderId;

    private String sender;

    @Convert(converter = UserProfileConverter.class)
    private String senderProfile;

    private String message;

    private LocalDateTime sendTime;

    public static Chat of(
            Long id,
            Long crewId,
            Long senderId,
            String sender,
            String senderProfile,
            String message,
            LocalDateTime sendTime
    ) {
        return new Chat(
                id,
                crewId,
                senderId,
                sender,
                senderProfile,
                message,
                sendTime
        );
    }

    private Chat(
            Long id,
            Long crewId,
            Long senderId,
            String sender,
            String senderProfile,
            String message,
            LocalDateTime sendTime
    ) {
        this.id = id;
        this.crewId = crewId;
        this.senderId = senderId;
        this.sender = sender;
        this.senderProfile = senderProfile;
        this.message = message;
        this.sendTime = sendTime;
    }
}
