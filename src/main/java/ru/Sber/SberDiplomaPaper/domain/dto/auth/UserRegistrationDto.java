package ru.Sber.SberDiplomaPaper.domain.dto.auth;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegistrationDto {
    @NotNull(message = " поле Email пустое.")
    @Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", message = "Email не соответствует формату")
    private String email;
    @NotNull(message = " поле для ввода пароля пустое")
    @Pattern(regexp = "[A-Za-z0-9]{8,16}", message = "Пароль должен содержать от 8 до 16 символов")
    private String password;
    @NotNull(message = "Необходимо указать пароль для подтверждения")
    private String passwordConfirm;
    @NotNull(message = "Необходимо указать имя")
    @Pattern(regexp = "[A-Za-z]{2,}", message = "Имя должно содержать от 2 до 50 символов")
    private String name;
}
