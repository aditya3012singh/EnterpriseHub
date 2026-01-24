package com.aditya.enterprisehub.repository;

import com.aditya.enterprisehub.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByBookingId(Long bookingId);
}