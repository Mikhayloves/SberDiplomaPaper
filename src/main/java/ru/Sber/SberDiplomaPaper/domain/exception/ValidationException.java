package ru.Sber.SberDiplomaPaper.domain.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private final List<String> errors;

    public ValidationException(List<FieldError> errros) {
        this.errors = new ArrayList<>();
        for (FieldError err : errros) {
            this.errors.add(err.getDefaultMessage());
        }
    }
}
