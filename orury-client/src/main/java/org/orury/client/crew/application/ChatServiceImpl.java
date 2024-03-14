package org.orury.client.crew.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.orury.domain.chat.ChatDto;
import org.orury.domain.crew.domain.dto.CrewDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ChatServiceImpl implements ChatService {
//    private final MongoDBRepository mongoDBRepository;

    @Override
    public void sendMessage(ChatDto chatDto) {
        // 메세지 전송
//        var chat = chatDto.toEntity();
//        mongoDBRepository.save(chat);

    }

    @Override
    public List<ChatDto> getMessages(CrewDto crewDto) {
        return null;
    }
}
