package com.aditya.enterprisehub.services;

import com.aditya.enterprisehub.entity.Booking;
import com.aditya.enterprisehub.entity.ChatMessage;
import com.aditya.enterprisehub.entity.ChatRoom;
import com.aditya.enterprisehub.entity.User;
import com.aditya.enterprisehub.entity.enums.BookingStatus;
import com.aditya.enterprisehub.repository.BookingRepository;
import com.aditya.enterprisehub.repository.ChatMessageRepository;
import com.aditya.enterprisehub.repository.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository messageRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    public ChatRoom getOrCreateRoom(Long bookingId, Long userId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Booking must be confirmed or paid
        if (booking.getStatus() == BookingStatus.PENDING) {
            throw new RuntimeException("Chat not allowed yet");
        }

        // Ownership check
        boolean allowed =
                booking.getUser().getId().equals(userId) ||
                        booking.getProviderService()
                                .getProvider()
                                .getUser()
                                .getId()
                                .equals(userId);

        if (!allowed) {
            throw new RuntimeException("Unauthorized chat access");
        }

        return chatRoomRepository.findByBookingId(bookingId)
                .orElseGet(() ->
                        chatRoomRepository.save(
                                ChatRoom.builder()
                                        .booking(booking)
                                        .user(booking.getUser())
                                        .provider(
                                                booking.getProviderService().getProvider()
                                        )
                                        .build()
                        )
                );
    }

    @Transactional
    public ChatMessage sendMessage(
            Long roomId,
            Long senderId,
            String content
    ) {

        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        return messageRepository.save(
                ChatMessage.builder()
                        .chatRoom(room)
                        .sender(User.builder().id(senderId).build())
                        .content(content)
                        .read(false)
                        .build()
        );
    }

    public List<ChatMessage> getMessages(Long roomId) {
        return messageRepository.findByChatRoomIdOrderByCreatedAtAsc(roomId);
    }
}
