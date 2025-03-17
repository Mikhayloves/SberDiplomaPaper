package ru.Sber.SberDiplomaPaper.domain.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Data
public class ProductCreationDto {
    @Length(min = 5, max = 50, message = "Name must be between 5 and 50 characters")
    private String name;
    @Length(min = 5, max = 255, message = "Description must be between 5 and 255 characters")
    private String description;
    @Range(min = 0, max = 1_000_000, message = "Price must be between 0 and 1_000_000")
    private Double price;
}
