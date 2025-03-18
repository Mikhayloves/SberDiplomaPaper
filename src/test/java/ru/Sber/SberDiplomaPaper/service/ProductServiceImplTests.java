package ru.Sber.SberDiplomaPaper.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.Sber.SberDiplomaPaper.domain.dto.product.ProductCategoryDto;
import ru.Sber.SberDiplomaPaper.domain.model.Product;
import ru.Sber.SberDiplomaPaper.repository.ProductRepository;
import ru.Sber.SberDiplomaPaper.service.product.ProductServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
public class ProductServiceImplTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("Тест обновления продукта")
    public void givenProduct_whenUpdateProduct_thenProductIsUpdated() {
        // given
        Product product = new Product();
        product.setId(1L);
        product.setName("Updated Product");
        BDDMockito.given(productRepository.save(any(Product.class)))
                .willReturn(product);

        // when
        Product updatedProduct = productService.updateProduct(product);

        // then
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getId()).isEqualTo(1L);
        assertThat(updatedProduct.getName()).isEqualTo("Updated Product");
    }

    @Test
    @DisplayName("Тест получения продукта по ID")
    public void givenProductId_whenGetProduct_thenProductIsReturned() {
        // given
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        BDDMockito.given(productRepository.findById(anyLong()))
                .willReturn(Optional.of(product));

        // when
        Product foundProduct = productService.getProduct(1L);

        // then
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(1L);
        assertThat(foundProduct.getName()).isEqualTo("Test Product");
    }

    @Test
    @DisplayName("Тест получения несуществующего продукта по ID")
    public void givenNonExistentProductId_whenGetProduct_thenExceptionIsThrown() {
        // given
        BDDMockito.given(productRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> productService.getProduct(1L))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Тест получения списка продуктов с пагинацией")
    public void givenPageAndSize_whenGetProducts_thenProductsAreReturned() {
        // given
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        Page<Product> productPage = new PageImpl<>(Collections.singletonList(product));
        BDDMockito.given(productRepository.findAll(any(PageRequest.class)))
                .willReturn(productPage);

        // when
        List<Product> products = productService.getProducts(0, 10);

        // then
        assertThat(products).isNotEmpty();
        assertThat(products.get(0).getName()).isEqualTo("Test Product");
    }

    @Test
    @DisplayName("Тест удаления продукта по ID")
    public void givenProductId_whenDeleteProduct_thenProductIsDeleted() {
        // given
        Long productId = 1L;
        BDDMockito.willDoNothing().given(productRepository).deleteById(anyLong());

        // when
        productService.deleteProduct(productId);

        // then
        BDDMockito.then(productRepository).should().deleteById(productId);
    }

    @Test
    @DisplayName("Тест привязки категории к продукту")
    public void givenProductIdAndCategoryId_whenAttachCategory_thenCategoryIsAttached() {
        // given
        Long productId = 1L;
        Long categoryId = 1L;
        BDDMockito.given(productRepository.existsById(anyLong()))
                .willReturn(true);
        BDDMockito.willDoNothing().given(productRepository).attachCategory(anyLong(), anyLong());

        // when
        ProductCategoryDto result = productService.attachCategory(productId, categoryId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getProductId()).isEqualTo(productId);
        assertThat(result.getCategoryId()).isEqualTo(categoryId);
    }

    @Test
    @DisplayName("Тест привязки категории к несуществующему продукту")
    public void givenNonExistentProductId_whenAttachCategory_thenExceptionIsThrown() {
        // given
        Long productId = 1L;
        Long categoryId = 1L;
        BDDMockito.given(productRepository.existsById(anyLong()))
                .willReturn(false);

        // when, then
        assertThatThrownBy(() -> productService.attachCategory(productId, categoryId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Продукт не найден");
    }

    @Test
    @DisplayName("Тест отвязки категории от продукта")
    public void givenProductIdAndCategoryId_whenDetachCategory_thenCategoryIsDetached() {
        // given
        Long productId = 1L;
        Long categoryId = 1L;
        BDDMockito.willDoNothing().given(productRepository).detachCategory(anyLong(), anyLong());

        // when
        ProductCategoryDto result = productService.detachCategory(productId, categoryId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getProductId()).isEqualTo(productId);
        assertThat(result.getCategoryId()).isEqualTo(categoryId);
    }

    @Test
    @DisplayName("Тест получения продуктов по категории")
    public void givenCategoryId_whenGetProductsByCategory_thenProductsAreReturned() {
        // given
        Long categoryId = 1L;
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        BDDMockito.given(productRepository.findByCategoryId(anyLong(), any(PageRequest.class)))
                .willReturn(Collections.singletonList(product));

        // when
        List<Product> products = productService.getProductsByCategory(categoryId, 0, 10);

        // then
        assertThat(products).isNotEmpty();
        assertThat(products.get(0).getName()).isEqualTo("Test Product");
    }
}


