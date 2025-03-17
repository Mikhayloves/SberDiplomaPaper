package ru.Sber.SberDiplomaPaper.service.confirmToken;

import ru.Sber.SberDiplomaPaper.domain.model.ConfirmToken;
import ru.Sber.SberDiplomaPaper.domain.model.User;

import java.util.UUID;

public interface ConfirmTokenService {

    ConfirmToken generateToken(User user);

    ConfirmToken getConfirmToken(UUID id);

    void deleteTokensByUser(User user);
}
