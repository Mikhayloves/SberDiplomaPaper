package ru.Sber.SberDiplomaPaper.service;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.Sber.SberDiplomaPaper.domain.exception.ResourceNotFoundException;
import ru.Sber.SberDiplomaPaper.domain.model.Order;
import ru.Sber.SberDiplomaPaper.domain.model.OrderedProduct;
import ru.Sber.SberDiplomaPaper.repository.OrderRepository;
import ru.Sber.SberDiplomaPaper.repository.OrderedProductRepository;
import ru.Sber.SberDiplomaPaper.service.order.OrderImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class OrderImplTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderedProductRepository orderedProductRepository;

    @InjectMocks
    private OrderImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Тест создания заказа")
    public void givenOrder_whenCreateOrder_thenOrderIsCreated() {
        // given
        Order order = new Order();
        order.setId(1L);
        order.setStatus("PENDING");

        OrderedProduct orderedProduct = new OrderedProduct();
        orderedProduct.setId(1L);
        orderedProduct.setQuantity(2);

        order.setOrderedProducts(Collections.singletonList(orderedProduct));

        BDDMockito.given(orderRepository.save(any(Order.class)))
                .willReturn(order);
        BDDMockito.given(orderedProductRepository.saveAll(any(List.class)))
                .willReturn(Collections.singletonList(orderedProduct));

        // when
        Order createdOrder = orderService.createOrder(order);

        // then
        assertThat(createdOrder).isNotNull();
        assertThat(createdOrder.getId()).isEqualTo(1L);
        assertThat(createdOrder.getStatus()).isEqualTo("PENDING");
        assertThat(createdOrder.getOrderedProducts()).isNotEmpty();
        assertThat(createdOrder.getOrderedProducts().get(0).getQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("Тест обновления статуса заказа")
    public void givenOrderIdAndStatus_whenUpdateOrderStatus_thenStatusIsUpdated() {
        // given
        Long orderId = 1L;
        String status = "COMPLETED";

        BDDMockito.willDoNothing().given(orderRepository).updateStatusById(anyLong(), any(String.class));

        // when
        orderService.updateOrderStatus(orderId, status);

        // then
        BDDMockito.then(orderRepository).should().updateStatusById(orderId, status);
    }

    @Test
    @DisplayName("Тест получения заказов пользователя")
    public void givenUserId_whenGetUserOrders_thenOrdersAreReturned() {
        // given
        Long userId = 1L;
        Order order = new Order();
        order.setId(1L);
        order.setStatus("PENDING");

        BDDMockito.given(orderRepository.findOrderByUserId(anyLong()))
                .willReturn(Collections.singletonList(order));

        // when
        List<Order> userOrders = orderService.getUserOrders(userId);

        // then
        assertThat(userOrders).isNotEmpty();
        assertThat(userOrders.get(0).getId()).isEqualTo(1L);
        assertThat(userOrders.get(0).getStatus()).isEqualTo("PENDING");
    }

    @Test
    @DisplayName("Тест получения заказа по ID")
    public void givenOrderId_whenGetOrderById_thenOrderIsReturned() {
        // given
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        order.setStatus("PENDING");

        BDDMockito.given(orderRepository.findById(anyLong()))
                .willReturn(Optional.of(order));

        // when
        Order foundOrder = orderService.getOrderById(orderId);

        // then
        assertThat(foundOrder).isNotNull();
        assertThat(foundOrder.getId()).isEqualTo(orderId);
        assertThat(foundOrder.getStatus()).isEqualTo("PENDING");
    }

    @Test
    @DisplayName("Тест получения несуществующего заказа по ID")
    public void givenNonExistentOrderId_whenGetOrderById_thenExceptionIsThrown() {
        // given
        Long orderId = 1L;
        BDDMockito.given(orderRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> orderService.getOrderById(orderId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Order not found " + orderId);
    }
}