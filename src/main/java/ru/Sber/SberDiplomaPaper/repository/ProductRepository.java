package ru.Sber.SberDiplomaPaper.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.Sber.SberDiplomaPaper.domain.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query(value = "INSERT INTO product_category (product_id, category_id) VALUES (:productId, :categoryId)", nativeQuery = true)
    void attachCategory(@Param("productId") Long productId, @Param("categoryId") Long categoryId);

    @Modifying
    @Query(value = "DELETE FROM product_category WHERE product_id = :productId AND category_id = :categoryId", nativeQuery = true)
    void detachCategory(@Param("productId") Long productId, @Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
}

