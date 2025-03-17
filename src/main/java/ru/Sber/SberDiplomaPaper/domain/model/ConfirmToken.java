package ru.Sber.SberDiplomaPaper.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
public class ConfirmToken {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    private User user;

    @CreationTimestamp
    private Timestamp createdAt;

    @Override
    public String toString() {
        return "ConfirmToken{" +
                "id=" + id +
                '}';
    }
}
