package ru.Sber.SberDiplomaPaper.service.jwt;

import ru.Sber.SberDiplomaPaper.domain.model.User;

public interface JwtService {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);
}
