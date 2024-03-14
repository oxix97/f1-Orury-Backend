package org.orury.client.crew.interfaces;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.orury.client.chat.application.ChatFacade;
import org.orury.domain.chat.ChatDto;
import org.orury.domain.user.domain.dto.UserPrincipal;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat-rooms")
@Controller
public class ChatController {
    private final ChatFacade chatFacade;

    private final SimpMessagingTemplate template;

    /**
     * 특정 채팅방의 채팅들을 불러옴
     *
     * @param id 채팅방 id
     */
    @GetMapping("/{id}")
    public String getChats(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserPrincipal principal,
            Model model
    ) {
        var chats = chatFacade.getRoomChats();
        model.addAttribute("user", principal);
        model.addAttribute("chats", chats);

        return "crew";
    }

    /**
     * 채팅방의 목록을 불러옴 -> 나중에 관리자 기능으로 변경하면 되나?
     */
    @GetMapping
    public String getChatRooms(
            @AuthenticationPrincipal UserPrincipal principal,
            Model model
    ) {
        var chatRooms = chatFacade.getChatRooms();
        model.addAttribute("chatRooms", chatRooms);
        return "list";
    }

    /**
     * 새로운 크루 채팅방 개설 -> 크루 생성시 자동으로 생성되도록
     */
    @PostMapping
    public void createRoom(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        chatFacade.createCrewChat();
    }

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatDto chatDto) {
        log.info("enter()");
        var msg = chatDto.message() + "님이 채팅방에 참여하였습니다.";
        template.convertAndSend("/sub/chat/" + chatDto.crewId(), msg);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatDto chatDto) {
        log.info("chatDto(chatDto = {})", chatDto);
        var msg = chatDto.message() + "님이 채팅방에 참여하였습니다.";
//        chatFacade.create(chatDto);
        template.convertAndSend("/sub/chat/" + chatDto.crewId(), msg);
    }
}
