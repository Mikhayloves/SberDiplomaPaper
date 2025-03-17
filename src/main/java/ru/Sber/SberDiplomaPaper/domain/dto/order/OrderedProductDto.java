package ru.Sber.SberDiplomaPaper.domain.dto.order;

import lombok.Data;


@Data
public class OrderedProductDto {
    private Long id;
    private Long productId;
    private Integer quantity;
}