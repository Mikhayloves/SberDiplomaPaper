package ru.Sber.SberDiplomaPaper.domain.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreationDto {
    private List<OrderedProductCreationDto> orderedProducts;
    private Long userId;
    private String status;
}
