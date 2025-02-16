package com.rs.whatsapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class ChatResponse {
    String id;
    String name;
    long unreadCount;
    String lastMessage;
    LocalDateTime lastMessageTime;
    boolean isOnline;
    String senderId;
    String receiverId;

}
