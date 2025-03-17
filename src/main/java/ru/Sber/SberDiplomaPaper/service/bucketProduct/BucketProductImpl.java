package ru.Sber.SberDiplomaPaper.service.bucketProduct;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.Sber.SberDiplomaPaper.domain.model.BucketProduct;
import ru.Sber.SberDiplomaPaper.domain.model.Product;
import ru.Sber.SberDiplomaPaper.domain.model.User;
import ru.Sber.SberDiplomaPaper.repository.BucketProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BucketProductImpl implements BucketProductService {
    private final BucketProductRepository bucketProductRepository;


    @Override
    public BucketProduct addProduct(Long productId, Long userId, Integer count) {
        BucketProduct bucketProduct = new BucketProduct();

        Product product = new Product();
        product.setId(productId);

        User user = new User();
        user.setId(userId);

        bucketProduct.setUser(user);
        bucketProduct.setProduct(product);
        bucketProduct.setCount(count);

        return bucketProductRepository.save(bucketProduct);
    }

    @Override
    public BucketProduct updateCount(Long id, Integer count) {
        // Обновляем счетчик
        bucketProductRepository.updateCountById(id, count);

        // Извлекаем обновлённый объект из базы данных
        return bucketProductRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product не найден"));
    }

    @Override
    public List<BucketProduct> findByUserId(Long productId) {
        return bucketProductRepository.findByProductId(productId);
    }

    @Override
    public void delete(Long id) {
        bucketProductRepository.deleteById(id);
    }
}
