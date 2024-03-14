package org.orury.client.crew.application;

import org.orury.domain.chat.ChatDto;
import org.orury.domain.crew.domain.dto.CrewDto;

import java.util.List;

public interface ChatService {
    void sendMessage(ChatDto chatDto);

    List<ChatDto> getMessages(CrewDto crewDto);
}
