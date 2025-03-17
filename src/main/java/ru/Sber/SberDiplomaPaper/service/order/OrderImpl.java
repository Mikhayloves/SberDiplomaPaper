package ru.Sber.SberDiplomaPaper.service.order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.Sber.SberDiplomaPaper.domain.exception.ResourceNotFoundException;
import ru.Sber.SberDiplomaPaper.domain.model.Order;
import ru.Sber.SberDiplomaPaper.domain.model.OrderedProduct;
import ru.Sber.SberDiplomaPaper.repository.OrderRepository;
import ru.Sber.SberDiplomaPaper.repository.OrderedProductRepository;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderedProductRepository orderedProductRepository;

    @Override
    public Order createOrder(Order order) {
        List<OrderedProduct> orderedProducts = order.getOrderedProducts();
        order.setOrderedProducts(null);
        Order savedOrder = orderRepository.save(order);
        orderedProducts.forEach(o -> {
                    o.setOrder(savedOrder);
                    o.setId(null);
                }
        );
        orderedProducts = orderedProductRepository.saveAll(orderedProducts);
        savedOrder.setOrderedProducts(orderedProducts);
        return savedOrder;
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        orderRepository.updateStatusById(orderId, status);
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findOrderByUserId(userId);
    }


    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found " + id));
    }
}
