package ru.Sber.SberDiplomaPaper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.Sber.SberDiplomaPaper.domain.dto.order.OrderCreationDto;
import ru.Sber.SberDiplomaPaper.domain.dto.order.OrderDto;
import ru.Sber.SberDiplomaPaper.domain.dto.order.OrderUpdateStatusDto;
import ru.Sber.SberDiplomaPaper.domain.dto.order.SimpleOrderDto;
import ru.Sber.SberDiplomaPaper.domain.model.Order;
import ru.Sber.SberDiplomaPaper.domain.util.DtoConverter;
import ru.Sber.SberDiplomaPaper.service.order.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final DtoConverter dtoConverter;

    public static final String CREATE_ORDER = "/create";
    public static final String UPDATE_ORDER = "/update";
    public static final String GET_ORDER = "/{id}";
    public static final String GET_USER_ORDERS = "/{user_id}/list";


    @GetMapping(GET_USER_ORDERS)
    public List<SimpleOrderDto> getUserOrders(@PathVariable("user_id") Long userId) {
        return dtoConverter.toDto(orderService.getUserOrders(userId), SimpleOrderDto.class);
    }

    @GetMapping(GET_ORDER)
    public OrderDto getOrder(@PathVariable("id") Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return dtoConverter.toDto(order, OrderDto.class);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(CREATE_ORDER)
    public OrderDto createOrder(@RequestBody OrderCreationDto dto) {
        Order order = dtoConverter.toModel(dto, Order.class);
        order.setId(null);
        return dtoConverter.toDto(orderService.createOrder(order), OrderDto.class);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(UPDATE_ORDER)
    public void updateOrderStatus(@RequestBody OrderUpdateStatusDto dto) {
        orderService.updateOrderStatus(dto.getId(), dto.getStatus());
    }




}
