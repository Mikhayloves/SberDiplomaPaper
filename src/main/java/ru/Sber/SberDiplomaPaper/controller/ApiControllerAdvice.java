package ru.Sber.SberDiplomaPaper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.Sber.SberDiplomaPaper.domain.dto.error.ErrorDto;
import ru.Sber.SberDiplomaPaper.domain.exception.AuthException;
import ru.Sber.SberDiplomaPaper.domain.exception.ResourceNotFoundException;
import ru.Sber.SberDiplomaPaper.domain.exception.ValidationException;

import java.util.List;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorDto handleValidationException(ValidationException ex) {
        return new ErrorDto(ex.getErrors());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ErrorDto handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ErrorDto(List.of(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public ErrorDto handleAuthException(AuthException e) {
        return new ErrorDto(List.of(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorDto handleException(Exception ex) {
        return new ErrorDto(List.of(ex.getMessage()));
    }

}
