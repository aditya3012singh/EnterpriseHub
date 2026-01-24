package com.aditya.enterprisehub.controllers;

import com.aditya.enterprisehub.config.auth.CustomUserDetails;
import com.aditya.enterprisehub.entity.ChatMessage;
import com.aditya.enterprisehub.entity.ChatRoom;
import com.aditya.enterprisehub.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/room/{bookingId}")
    public ChatRoom openRoom(
            @PathVariable Long bookingId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return chatService.getOrCreateRoom(
                bookingId,
//                user.getUser().getId()
                user.getUserId()
        );
    }

    @GetMapping("/messages/{roomId}")
    public List<ChatMessage> getMessages(@PathVariable Long roomId) {
        return chatService.getMessages(roomId);
    }
}
