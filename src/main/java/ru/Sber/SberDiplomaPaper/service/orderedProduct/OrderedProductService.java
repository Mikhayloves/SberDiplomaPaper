package ru.Sber.SberDiplomaPaper.service.orderedProduct;

import ru.Sber.SberDiplomaPaper.domain.model.OrderedProduct;

import java.util.List;
import java.util.Optional;

public interface OrderedProductService {
    OrderedProduct createOrderedProduct(OrderedProduct orderedProduct);

    List<OrderedProduct> listOrderedProducts();

    Optional<OrderedProduct> getOrderedProductById(Long id);
}
