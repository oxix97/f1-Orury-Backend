package org.orury.client.chat.application;

import org.orury.domain.chat.domain.dto.ChatDto;
import org.orury.domain.crew.domain.dto.CrewDto;

import java.util.List;

public interface ChatService {
    void sendMessage(ChatDto chatDto);

    List<ChatDto> getMessages(CrewDto crewDto);
}
