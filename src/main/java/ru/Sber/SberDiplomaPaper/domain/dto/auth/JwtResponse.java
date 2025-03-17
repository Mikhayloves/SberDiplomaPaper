package ru.Sber.SberDiplomaPaper.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
}
