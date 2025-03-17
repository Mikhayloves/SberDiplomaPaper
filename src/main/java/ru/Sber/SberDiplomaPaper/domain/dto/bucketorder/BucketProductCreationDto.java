package ru.Sber.SberDiplomaPaper.domain.dto.bucketorder;

import lombok.Data;

@Data
public class BucketProductCreationDto {
    private Long productId;
    private Long userId;
    private Integer count;
}
