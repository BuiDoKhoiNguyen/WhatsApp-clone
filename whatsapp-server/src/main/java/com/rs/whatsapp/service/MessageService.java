package com.rs.whatsapp.service;

import com.rs.whatsapp.domain.enums.NotificationType;
import com.rs.whatsapp.domain.entity.Chat;
import com.rs.whatsapp.domain.entity.Message;
import com.rs.whatsapp.domain.enums.MessageState;
import com.rs.whatsapp.domain.enums.MessageType;
import com.rs.whatsapp.domain.model.Notification;
import com.rs.whatsapp.mapper.MessageMapper;
import com.rs.whatsapp.notification.NotificationService;
import com.rs.whatsapp.payload.request.MessageRequest;
import com.rs.whatsapp.payload.response.MessageResponse;
import com.rs.whatsapp.repository.ChatRepository;
import com.rs.whatsapp.repository.MessageRepository;
import com.rs.whatsapp.util.FileUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper messageMapper;
    private final FileService fileService;
    private final NotificationService notificationService;

    public void saveMessage(MessageRequest messageRequest) {
        Chat chat = chatRepository.findById(messageRequest.getChatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat not found with id: " + messageRequest.getChatId()));

        Message message = new Message();
        message.setChat(chat);
        message.setContent(messageRequest.getContent());
        message.setSenderId(messageRequest.getSenderId());
        message.setReceiverId(messageRequest.getReceiverId());
        message.setState(MessageState.SENT);
        message.setType(messageRequest.getType());

        messageRepository.save(message);

        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .content(messageRequest.getContent())
                .messageType(messageRequest.getType())
                .senderId(messageRequest.getSenderId())
                .receiverId(messageRequest.getReceiverId())
                .type(NotificationType.MESSAGE)
                .chatName(chat.getChatName(message.getSenderId()))
                .build();

        notificationService.sendNotification(message.getReceiverId(), notification);
    }

    public List<MessageResponse> findChatMessage(String chatId) {
        return messageRepository.findByChatId(chatId)
                .stream()
                .map(messageMapper::toMessageResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void setMessagesToSeen(String chatId, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found with id: " + chatId));

        final String receiverId = getReceiverId(chat, authentication);
        messageRepository.setMessagesToSeenByChat(chatId, MessageState.SEEN);

        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .senderId(getSenderId(chat, authentication))
                .receiverId(receiverId)
                .type(NotificationType.SEEN)
                .build();

        notificationService.sendNotification(receiverId, notification);
    }

    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found with id: " + chatId));

        final String senderId = getSenderId(chat, authentication);
        final String receiverId = getReceiverId(chat, authentication);

        final String filePath = fileService.saveFile(file, senderId);

        Message message = new Message();
        message.setChat(chat);
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setType(MessageType.IMAGE);
        message.setState(MessageState.SENT);
        message.setMediaFilePath(filePath);
        messageRepository.save(message);

        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .senderId(senderId)
                .receiverId(receiverId)
                .type(NotificationType.IMAGE)
                .messageType(MessageType.IMAGE)
                .media(FileUtils.readFileFromLocation(filePath))
                .build();

        notificationService.sendNotification(receiverId, notification);
    }

    private String getSenderId(Chat chat, Authentication authentication) {
        if(chat.getSender().getId().equals(authentication.getName())) {
            return chat.getSender().getId();
        } else {
            return chat.getReceiver().getId();
        }
    }

    private String getReceiverId(Chat chat, Authentication authentication) {
        if(chat.getSender().getId().equals(authentication.getName())) {
            return chat.getReceiver().getId();
        } else {
            return chat.getSender().getId();
        }
    }
}
