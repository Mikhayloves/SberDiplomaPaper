package ru.Sber.SberDiplomaPaper.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.Sber.SberDiplomaPaper.domain.dto.product.ProductCategoryDto;
import ru.Sber.SberDiplomaPaper.domain.model.Product;
import ru.Sber.SberDiplomaPaper.repository.ProductRepository;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Product> getProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size)).toList();
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductCategoryDto attachCategory(Long productId, Long categoryId) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Продукт не найден");
        }
        productRepository.attachCategory(productId, categoryId);
        ProductCategoryDto dto = new ProductCategoryDto();
        dto.setProductId(productId);
        dto.setCategoryId(categoryId);
        return dto;
    }

    @Override
    public ProductCategoryDto detachCategory(Long productId, Long categoryId) {
        productRepository.detachCategory(productId, categoryId);
        ProductCategoryDto dto = new ProductCategoryDto();
        dto.setProductId(productId);
        dto.setCategoryId(categoryId);
        return dto;
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId, int page, int size) {
       return productRepository.findByCategoryId(categoryId, PageRequest.of(page, size));
    }
}
