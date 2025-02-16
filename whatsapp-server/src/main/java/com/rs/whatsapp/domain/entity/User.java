package com.rs.whatsapp.domain.entity;

import com.rs.whatsapp.domain.constant.UserConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@FieldDefaults(level = PRIVATE)
public class User extends BaseAuditingEntity {
    @Id
    String id;

    String firstName;
    String lastName;
    String email;

    LocalDateTime lastSeen;

    @OneToMany(mappedBy = "sender")
    List<Chat> chatsAsSender;

    @OneToMany(mappedBy = "receiver")
    List<Chat> chatsAsReceiver;

    @Transient
    public boolean isOnline() {
        return lastSeen != null && lastSeen.isAfter(LocalDateTime.now().plusMinutes(5));
    }

}
