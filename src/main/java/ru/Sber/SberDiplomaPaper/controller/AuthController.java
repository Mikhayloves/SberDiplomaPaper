package ru.Sber.SberDiplomaPaper.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.Sber.SberDiplomaPaper.config.UserAuthentication;
import ru.Sber.SberDiplomaPaper.domain.dto.auth.JwtResponse;
import ru.Sber.SberDiplomaPaper.domain.dto.auth.UserLoginDto;
import ru.Sber.SberDiplomaPaper.domain.dto.auth.UserRegistrationDto;
import ru.Sber.SberDiplomaPaper.domain.dto.user.UserDto;
import ru.Sber.SberDiplomaPaper.domain.exception.AuthException;
import ru.Sber.SberDiplomaPaper.domain.exception.ValidationException;
import ru.Sber.SberDiplomaPaper.domain.model.User;
import ru.Sber.SberDiplomaPaper.domain.model.UserDetailsImpl;
import ru.Sber.SberDiplomaPaper.domain.model.UserRole;
import ru.Sber.SberDiplomaPaper.domain.util.DtoConverter;
import ru.Sber.SberDiplomaPaper.domain.util.UserValidator;
import ru.Sber.SberDiplomaPaper.service.auth.AuthService;
import ru.Sber.SberDiplomaPaper.service.jwt.JwtService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    public static final String LOGIN = "/login";
    public static final String REGISTRATION = "/registration";
    public static final String CONFIRM = "/confirm";
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserValidator userValidator;
    private final DtoConverter dtoConverter;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @PostMapping(LOGIN)
    public JwtResponse login(@RequestBody UserLoginDto userLoginDto) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(userLoginDto.getEmail());
            Authentication authentication = new UserAuthentication(userDetails, userLoginDto.getPassword());
            authentication = authenticationManager.authenticate(authentication);
            if (!authentication.isAuthenticated()) {
                throw new AuthException("Wrong password");
            }
            return new JwtResponse(jwtService.generateAccessToken(userDetails.getUser()), jwtService.generateRefreshToken(userDetails.getUser()));
        } catch (UsernameNotFoundException e) {
            throw new AuthException("Username not found");
        }
    }

    @PostMapping(REGISTRATION)
    public UserDto register(@RequestBody @Validated UserRegistrationDto userRegistrationDto, BindingResult errors) {
        userValidator.validate(userRegistrationDto, errors);
        if (errors.hasErrors()) {
            throw new ValidationException(errors.getFieldErrors());
        }
        User model = dtoConverter.toModel(userRegistrationDto, User.class);
        model.setRole(UserRole.USER);
        model.setPasswordHash(passwordEncoder.encode(userRegistrationDto.getPassword()));
        authService.register(model);
        return dtoConverter.toDto(model, UserDto.class);
    }

    @GetMapping(CONFIRM)
    public void confirm(@RequestParam("token") String token) {
        authService.confirmToken(token);
    }

}
