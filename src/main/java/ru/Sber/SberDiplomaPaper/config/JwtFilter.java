package ru.Sber.SberDiplomaPaper.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.Sber.SberDiplomaPaper.domain.exception.AuthException;
import ru.Sber.SberDiplomaPaper.domain.model.User;
import ru.Sber.SberDiplomaPaper.domain.model.UserDetailsImpl;
import ru.Sber.SberDiplomaPaper.domain.model.UserRole;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final Environment environment;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            if (request.getRequestURI().startsWith("/api/auth")) {
                filterChain.doFilter(request, response);
                return;
            }
            String secret = environment.getProperty("jwt.secret");
            String token = request.getHeader("authorization");
            if (token == null) {
                throw new AuthException("No token");
            }
            token = token.substring(7);
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("sber")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String role = decodedJWT.getClaim("role").asString();
            String email = decodedJWT.getClaim("email").asString();
            Long id = decodedJWT.getClaim("id").asLong();
            User user = new User();
            user.setRole(UserRole.valueOf(role));
            user.setEmail(email);
            user.setId(id);
            SecurityContextHolder.getContext().setAuthentication(new UserSuccessAuthentication(new UserDetailsImpl(user)));
        } catch (JWTVerificationException ex) {
            throw new ServletException();
        }
        filterChain.doFilter(request, response);
    }
}
