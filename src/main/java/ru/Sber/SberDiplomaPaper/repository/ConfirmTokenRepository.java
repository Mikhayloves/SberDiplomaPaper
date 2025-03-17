package ru.Sber.SberDiplomaPaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Sber.SberDiplomaPaper.domain.model.ConfirmToken;
import ru.Sber.SberDiplomaPaper.domain.model.User;

import java.util.UUID;

@Repository
public interface ConfirmTokenRepository extends JpaRepository<ConfirmToken, UUID> {
    void deleteAllByUser(User user);
}
