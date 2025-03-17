package ru.Sber.SberDiplomaPaper.domain.dto.order;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private List<OrderedProductDto> orderedProducts;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String status;
}