package ru.Sber.SberDiplomaPaper.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.Sber.SberDiplomaPaper.domain.model.UserDetailsImpl;

import java.util.Collection;

@RequiredArgsConstructor
public class UserSuccessAuthentication implements Authentication {
    private final UserDetailsImpl userDetails;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return userDetails.getPassword();
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        return userDetails.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    }

    @Override
    public String getName() {
        return userDetails.getUsername();
    }
}
