package com.rs.whatsapp.payload.response;

import com.rs.whatsapp.domain.enums.MessageState;
import com.rs.whatsapp.domain.enums.MessageType;
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
public class MessageResponse {
    Long id;
    String content;
    String senderId;
    String receiverId;
    MessageType type;
    MessageState state;
    LocalDateTime createdAt;
    byte[] media;
}
