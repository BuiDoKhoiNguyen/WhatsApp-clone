package com.rs.whatsapp.mapper;

import com.rs.whatsapp.domain.entity.Chat;
import com.rs.whatsapp.payload.response.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class ChatMapper {
    public ChatResponse toChatResponse(Chat chat, String senderId) {
        return ChatResponse.builder()
                .id(chat.getId())
                .name(chat.getChatName(senderId))
                .unreadCount(chat.getUnreadMessagesCount(senderId))
                .lastMessage(chat.getLastMessage())
                .lastMessageTime(chat.getLastMessageTime())
                .isOnline(chat.getReceiver().isOnline())
                .senderId(chat.getSender().getId())
                .receiverId(chat.getReceiver().getId())
                .build();
    }
}
