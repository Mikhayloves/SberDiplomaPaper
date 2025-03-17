package ru.Sber.SberDiplomaPaper.domain.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorDto {
    private List<String> errors;
}
