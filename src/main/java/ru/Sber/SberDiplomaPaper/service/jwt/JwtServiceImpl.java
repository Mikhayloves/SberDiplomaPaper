package ru.Sber.SberDiplomaPaper.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ru.Sber.SberDiplomaPaper.domain.model.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final Environment environment;


    @Override
    public String generateAccessToken(User user) {
        String secret = environment.getProperty("jwt.secret");
        int expiration = Integer.parseInt(environment.getProperty("jwt.expire.access"));
        Map<String, Object> headers = Map.of("alg", "HMAC256", "typ", "JWT");
        return JWT.create()
                .withHeader(headers)
                .withClaim("id", user.getId())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().name())
                .withIssuer("sber")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(expiration, ChronoUnit.MINUTES))
                .sign(Algorithm.HMAC256(secret));
    }

    @Override
    public String generateRefreshToken(User user) {
        String secret = environment.getProperty("jwt.secret");
        int expiration = Integer.parseInt(environment.getProperty("jwt.expire.refresh"));
        Map<String, Object> headers = Map.of("alg", "HMAC256", "typ", "JWT");
        return JWT.create()
                .withHeader(headers)
                .withClaim("id", user.getId())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().name())
                .withIssuer("sber")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(expiration, ChronoUnit.HOURS))
                .sign(Algorithm.HMAC256(secret));
    }
}
