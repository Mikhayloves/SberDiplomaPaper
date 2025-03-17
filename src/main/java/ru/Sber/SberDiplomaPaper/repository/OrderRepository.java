package ru.Sber.SberDiplomaPaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.Sber.SberDiplomaPaper.domain.model.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Modifying
    @Query("UPDATE Order o SET o.status=:status WHERE o.id=:id")
    void updateStatusById(@Param("id") Long id, @Param("status") String status);

    @Query("SELECT o FROM Order o JOIN FETCH o.orderedProducts WHERE o.user.id=:id")
    List<Order> findOrderByUserId(@Param("id") Long id);
}
