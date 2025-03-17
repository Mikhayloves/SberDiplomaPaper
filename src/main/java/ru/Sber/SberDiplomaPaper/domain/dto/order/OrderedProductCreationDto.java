package ru.Sber.SberDiplomaPaper.domain.dto.order;

import lombok.Data;

@Data
public class OrderedProductCreationDto {
    private Long productId;
    private Integer quantity;
}
