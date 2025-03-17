package ru.Sber.SberDiplomaPaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.Sber.SberDiplomaPaper.domain.model.BucketProduct;

import java.util.List;

@Repository
public interface BucketProductRepository extends JpaRepository<BucketProduct, Long> {
    List<BucketProduct> findByProductId(Long productId);

//    @Modifying
//    @Transactional
//    @Query("update BucketProduct set count=:count where id=:id")
//    BucketProduct updateCountById(@Param("id") Long id, @Param("count") Integer count);

    @Modifying
    @Query("UPDATE BucketProduct bp SET bp.count = :count WHERE bp.id = :id")
    int updateCountById(@Param("id") Long id, @Param("count") Integer count);



}
