package org.orury.domain.chat.domain.dto;

import org.orury.domain.chat.domain.entity.Chat;

import java.time.LocalDateTime;

public record ChatDto(
        Long crewId,
        Long senderId,
        String sender,
        String senderProfile,
        String message,
        LocalDateTime sendTime
) {
    public static ChatDto of(
            Long crewId,
            Long senderId,
            String sender,
            String senderProfile,
            String message
    ) {
        return new ChatDto(
                crewId,
                senderId,
                sender,
                senderProfile,
                message,
                LocalDateTime.now()
        );
    }

    public Chat toEntity() {
        return Chat.of(
                null,
                crewId,
                senderId,
                sender,
                senderProfile,
                message,
                sendTime
        );
    }
}
