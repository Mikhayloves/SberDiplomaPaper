package ru.Sber.SberDiplomaPaper.service.order;

import ru.Sber.SberDiplomaPaper.domain.model.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);

    void updateOrderStatus(Long orderId, String status);

    List<Order> getUserOrders(Long userId);

    Order getOrderById(Long id);
}
