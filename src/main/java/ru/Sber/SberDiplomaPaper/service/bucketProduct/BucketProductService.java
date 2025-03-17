package ru.Sber.SberDiplomaPaper.service.bucketProduct;

import ru.Sber.SberDiplomaPaper.domain.model.BucketProduct;

import java.util.List;


public interface BucketProductService {

    BucketProduct addProduct(Long productId, Long userId, Integer count);

    BucketProduct updateCount(Long id, Integer count);

    List<BucketProduct> findByUserId(Long userId);

    void delete(Long id);

}
