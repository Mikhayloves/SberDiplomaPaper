package ru.Sber.SberDiplomaPaper.domain.dto.order;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SimpleOrderDto {
    private Long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String status;
}
