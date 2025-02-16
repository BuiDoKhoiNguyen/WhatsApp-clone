package com.rs.whatsapp.service;

import com.rs.whatsapp.domain.entity.Chat;
import com.rs.whatsapp.domain.entity.User;
import com.rs.whatsapp.mapper.ChatMapper;
import com.rs.whatsapp.payload.response.ChatResponse;
import com.rs.whatsapp.repository.ChatRepository;
import com.rs.whatsapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMapper chatMapper;

    @Transactional
    public List<ChatResponse> getChatsByReceiverId(Authentication authentication) {
        String senderId = authentication.getName();
        return chatRepository.findChatsBySenderId(senderId)
                .stream()
                .map(chat -> chatMapper.toChatResponse(chat, senderId))
                .collect(Collectors.toList());
    }

    public String createChat(String senderId, String receiverId) {
        Optional<Chat> existingChat = chatRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        if(existingChat.isPresent()) {
            return existingChat.get().getId();
        }

        User sender = userRepository.findByPublicId(senderId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + senderId));

        User receiver = userRepository.findByPublicId(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + receiverId));

        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setReceiver(receiver);

        Chat savedChat = chatRepository.save(chat);
        return savedChat.getId();

    }
}
