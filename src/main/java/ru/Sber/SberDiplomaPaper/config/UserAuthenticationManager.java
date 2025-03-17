package ru.Sber.SberDiplomaPaper.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.Sber.SberDiplomaPaper.domain.model.UserDetailsImpl;

@Component
@RequiredArgsConstructor
public class UserAuthenticationManager implements AuthenticationManager {
    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserAuthentication userAuthentication = (UserAuthentication) authentication;
        String userPassword = userAuthentication.getPassword();
        UserDetailsImpl userDetails = (UserDetailsImpl) userAuthentication.getDetails();
        String passwordFromDB = userDetails.getPassword();
        boolean isAuthenticated = passwordEncoder.matches(userPassword, passwordFromDB) && userDetails.isEnabled();
        userAuthentication.setAuthenticated(isAuthenticated);
        return userAuthentication;
    }


}
