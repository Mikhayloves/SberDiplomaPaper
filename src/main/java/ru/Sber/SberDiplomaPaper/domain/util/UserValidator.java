package ru.Sber.SberDiplomaPaper.domain.util;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.Sber.SberDiplomaPaper.domain.dto.auth.UserRegistrationDto;
import ru.Sber.SberDiplomaPaper.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegistrationDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationDto user = (UserRegistrationDto) target;
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm", "", "Passwords don't match");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            errors.rejectValue("email", "", "Email address already in use");
        }
    }
}
