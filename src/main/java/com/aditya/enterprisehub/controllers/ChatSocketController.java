package com.aditya.enterprisehub.controllers;

import com.aditya.enterprisehub.dtos.ChatMessageRequest;
import com.aditya.enterprisehub.entity.ChatMessage;
import com.aditya.enterprisehub.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void send(ChatMessageRequest request) {

        ChatMessage message =
                chatService.sendMessage(
                        request.getRoomId(),
                        request.getSenderId(),
                        request.getContent()
                );

        messagingTemplate.convertAndSend(
                "/topic/chat/" + request.getRoomId(),
                message
        );
    }
}
