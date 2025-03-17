package ru.Sber.SberDiplomaPaper.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.Sber.SberDiplomaPaper.domain.exception.AuthException;
import ru.Sber.SberDiplomaPaper.domain.model.ConfirmToken;
import ru.Sber.SberDiplomaPaper.domain.model.User;
import ru.Sber.SberDiplomaPaper.service.confirmToken.ConfirmTokenService;
import ru.Sber.SberDiplomaPaper.service.mail.MailSenderService;
import ru.Sber.SberDiplomaPaper.service.mail.RegistrationEmailDetails;
import ru.Sber.SberDiplomaPaper.service.user.UserService;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final Environment environment;
    private final ConfirmTokenService confirmTokenService;
    private final MailSenderService mailSenderService;

    @Override
    public void register(User user) {
        user = userService.save(user);
        ConfirmToken confirmToken = confirmTokenService.generateToken(user);
        System.out.println(confirmToken);
        mailSenderService.sendEmail(
                RegistrationEmailDetails.builder()
                        .from(environment.getProperty("spring.mail.username"))
                        .to(user.getEmail())
                        .token(confirmToken.getId().toString())
                        .serverURL(environment.getProperty("email.serverURL"))
                        .build()
        );
    }

    @Override
    public void confirmToken(String token) {
        ConfirmToken confirmToken = confirmTokenService.getConfirmToken(UUID.fromString(token));

        Timestamp currentTime = Timestamp.from(Instant.now());
        Timestamp createdAt = confirmToken.getCreatedAt();
        Integer confirmTokenTimeMinutes = Integer.parseInt(environment.getProperty("confirm.expire"));
        if (createdAt.toInstant().plus(confirmTokenTimeMinutes, ChronoUnit.MINUTES).isBefore(currentTime.toInstant())) {
            throw new AuthException("Confirm token is expired");
        }
        User user = confirmToken.getUser();
        userService.updateEnabled(user.getId(), true);
        confirmTokenService.deleteTokensByUser(user);
    }
}
