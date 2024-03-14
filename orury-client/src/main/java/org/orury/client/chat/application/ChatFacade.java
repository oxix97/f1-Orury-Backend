package org.orury.client.chat.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.orury.domain.chat.domain.dto.ChatDto;
import org.orury.domain.chat.domain.entity.ChatRoom;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatFacade {
    private final ChatService chatService;
//    private final NotificationService notificationService;

    public void sendMessage(ChatDto request) {
        //메세지 송신
        chatService.sendMessage(request);
        //알림 송신
//        notificationService.sendAlert();
    }

    public List<ChatRoom> getChatRooms() {
        // 채팅방 목록 조회
        return null;
    }

    public List<ChatDto> getRoomChats() {
        // 채팅방의 채팅들 조회
        return null;
    }

    public void createCrewChat() {
        // 크루 채팅방 생성
    }
}
