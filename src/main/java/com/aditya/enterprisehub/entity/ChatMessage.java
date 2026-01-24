package com.aditya.enterprisehub.entity;

import com.aditya.enterprisehub.entity.common.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "chat_messages",
        indexes = {
                @Index(name = "idx_chat_room", columnList = "chat_room_id"),
                @Index(name = "idx_sender", columnList = "sender_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage extends AuditableEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_id")
    private User sender;

    @Column(nullable = false)
    private String content;

    private boolean read;
}
