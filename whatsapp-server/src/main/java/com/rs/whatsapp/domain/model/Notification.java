package com.rs.whatsapp.domain.model;

import com.rs.whatsapp.domain.enums.NotificationType;
import com.rs.whatsapp.domain.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class Notification {
    String chatId;
    String content;
    String senderId;
    String receiverId;
    String chatName;
    MessageType messageType;
    NotificationType type;
    byte[] media;
}
