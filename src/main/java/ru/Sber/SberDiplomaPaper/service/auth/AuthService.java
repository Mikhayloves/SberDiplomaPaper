package ru.Sber.SberDiplomaPaper.service.auth;

import ru.Sber.SberDiplomaPaper.domain.model.User;

public interface AuthService {

    void register(User user);

    void confirmToken(String token);

}
