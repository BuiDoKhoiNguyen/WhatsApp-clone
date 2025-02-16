package com.rs.whatsapp.repository;

import com.rs.whatsapp.domain.entity.Message;
import com.rs.whatsapp.domain.enums.MessageState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    @Query("SELECT m FROM Message m WHERE m.chat.id = :chatId ORDER BY m.createdDate DESC")
    List<Message> findByChatId(@Param("chatId") String chatId);

    @Modifying
    @Query("UPDATE Message m SET m.state = :newState WHERE m.chat.id = :chatId")
    void setMessagesToSeenByChat(@Param("chatId") String chatId, @Param("newState") MessageState state);
}
