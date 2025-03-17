package ru.Sber.SberDiplomaPaper.service.orderedProduct;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.Sber.SberDiplomaPaper.domain.model.OrderedProduct;
import ru.Sber.SberDiplomaPaper.repository.OrderedProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderedProductImpl implements OrderedProductService {
    private final OrderedProductRepository orderedProductRepository;

    @Override
    public OrderedProduct createOrderedProduct(OrderedProduct orderedProduct) {
        return orderedProductRepository.save(orderedProduct);
    }

    @Override
    public List<OrderedProduct> listOrderedProducts() {
        return orderedProductRepository.findAll();
    }

    @Override
    public Optional<OrderedProduct> getOrderedProductById(Long id) {
        return orderedProductRepository.findById(id);
    }
}


