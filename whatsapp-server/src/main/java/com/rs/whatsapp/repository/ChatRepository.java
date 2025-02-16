package com.rs.whatsapp.repository;

import com.rs.whatsapp.domain.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {
    @Query("SELECT DISTINCT c FROM Chat c WHERE (c.sender.id = :senderId AND c.receiver.id = :receiverId) OR (c.sender.id = :receiverId AND c.receiver.id = :senderId) ORDER BY c.createdDate DESC")
    Optional<Chat> findBySenderIdAndReceiverId(@Param("senderId") String senderId, @Param("receiverId") String receiverId);

    @Query("SELECT c FROM Chat c WHERE c.sender.id = :senderId ORDER BY c.createdDate DESC")
    List<Chat> findChatsBySenderId(@Param("senderId") String senderId);
}
