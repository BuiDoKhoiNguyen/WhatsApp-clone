package com.rs.whatsapp.domain.entity;

import com.rs.whatsapp.domain.constant.MessageConstants;
import com.rs.whatsapp.domain.enums.MessageState;
import com.rs.whatsapp.domain.enums.MessageType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
@FieldDefaults(level = PRIVATE)
public class Message extends BaseAuditingEntity {
    @Id
    @SequenceGenerator(
            name = "message_sequence",
            sequenceName = "message_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "message_sequence",
            strategy = GenerationType.SEQUENCE
    )
    Long id;

    @Column(columnDefinition = "TEXT")
    String content;

    @Enumerated(EnumType.STRING)
    MessageType type;

    @Enumerated(EnumType.STRING)
    MessageState state;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    Chat chat;

    @Column(name = "sender_id", nullable = false)
    String senderId;

    @Column(name = "receiver_id", nullable = false)
    String receiverId;

    String mediaFilePath;

}
