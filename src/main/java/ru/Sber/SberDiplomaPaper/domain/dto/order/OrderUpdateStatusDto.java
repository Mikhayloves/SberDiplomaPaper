package ru.Sber.SberDiplomaPaper.domain.dto.order;

import lombok.Data;

@Data
public class OrderUpdateStatusDto {
    private Long id;
    private String status;
}
