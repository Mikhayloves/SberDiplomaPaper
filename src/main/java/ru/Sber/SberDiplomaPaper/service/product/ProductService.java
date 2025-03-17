package ru.Sber.SberDiplomaPaper.service.product;

import ru.Sber.SberDiplomaPaper.domain.dto.product.ProductCategoryDto;
import ru.Sber.SberDiplomaPaper.domain.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);

    Product updateProduct(Product product);

    Product getProduct(Long id);

    List<Product> getProducts(int page, int size);

    void deleteProduct(Long id);

    ProductCategoryDto attachCategory(Long productId, Long categoryId);

    ProductCategoryDto detachCategory(Long productId, Long categoryId);

    List<Product> getProductsByCategory(Long categoryId, int page, int size);

}
