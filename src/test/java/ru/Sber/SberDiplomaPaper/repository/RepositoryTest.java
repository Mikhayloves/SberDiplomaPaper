package ru.Sber.SberDiplomaPaper.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.Sber.SberDiplomaPaper.domain.model.*;
import util.DataUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты для репозитория")
@DataJpaTest
@Transactional
@Rollback
public class RepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BucketProductRepository bucketProductRepository;

    private Category category;

    private Product product;

    private BucketProduct bucketProduct;

    private Category newCategory;




    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        productRepository.deleteAll();
        orderRepository.deleteAll();
        // Создаем тестовые данные
        product = Product.builder()
                .name("Test Product")
                .description("Test Description")
                .price(100.0)
                .build();
        productRepository.save(product);

        category = Category.builder()
                .name("Test Category")
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .products(List.of(product))
                .build();
        bucketProduct = BucketProduct.builder()
                .product(product)
                .count(5)
                .build();
        bucketProductRepository.save(bucketProduct);
        newCategory = new Category();
        newCategory.setName("Gadgets");
        newCategory = categoryRepository.save(newCategory);
    }


    @Test
    @DisplayName("Тест по созданию пользователя")
    public void givenUser_whensave_thenCreatedUser() {
        //given
        User user = DataUtils.getAntonByTransient();
        //when
        User savedUser = userRepository.save(user);
        //then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
    }
    @Test
    @DisplayName("Тест по созданию продукта")
    public void givenProduct_whensave_thenCreatedProduct() {
        Product product = DataUtils.getProductByTransient();
        Product savedProduct = productRepository.save(product);
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
    }
    @Test
    @DisplayName("Тест удаления категории продукта методом detachCategory")
    public void whenDetachCategory_thenCategoryIsRemoved() {
        // Удаляем связь между продуктом и категорией
        productRepository.detachCategory(product.getId(), category.getId());

        // Проверяем, что продукт больше не связан с категорией
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> products = productRepository.findByCategoryId(category.getId(), pageable);

        assertThat(products).doesNotContain(product);
    }
    @Test
    @DisplayName("Тест добавления категории продукту методом attachCategory")
    public void whenAttachCategory_thenCategoryIsAdded() {
        // Добавляем новую категорию продукту
        productRepository.attachCategory(product.getId(), newCategory.getId());

        // Проверяем, что продукт теперь связан с новой категорией
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> products = productRepository.findByCategoryId(newCategory.getId(), pageable);

        assertThat(products).contains(product);
    }

    @Test
    @DisplayName("Тест по созданию заказа")
    public void givenOrder_whensave_thenCreatedOrder() {
        List<OrderedProduct> orderedProducts;

        // given
        // Создаем и сохраняем пользователя
        User user1 = User.builder()
                .name("Antony")
                .email("Antony@yandex.ru")
                .passwordHash("8456111315")
                .role(UserRole.USER)
                .build();
        User savedUser = userRepository.save(user1);

        // Создаем и сохраняем продукты
        Product product1 = Product.builder()
                .name("Bear bear")
                .description("Крафтовое пиво с апельсиновыми нотками")
                .price(100.0)
                .build();
        Product savedProduct1 = productRepository.save(product1);

        Product product2 = Product.builder()
                .name("IPA")
                .description("Индийский светлый эль")
                .price(150.0)
                .build();
        Product savedProduct2 = productRepository.save(product2);

        // Создаем заказанные продукты
        OrderedProduct orderedProduct1 = OrderedProduct.builder()
                .product(savedProduct1)
                .quantity(2)
                .build();

        OrderedProduct orderedProduct2 = OrderedProduct.builder()
                .product(savedProduct2)
                .quantity(1)
                .build();

        orderedProducts = new ArrayList<>();
        orderedProducts.add(orderedProduct1);
        orderedProducts.add(orderedProduct2);

        // Создаем заказ
        Order order = Order.builder()
                .user(savedUser)
                .orderedProducts(orderedProducts)
                .status("PROCESSING")
                .build();

        // when
        Order savedOrder = orderRepository.save(order);

        // then
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isNotNull();
        assertThat(savedOrder.getUser()).isEqualTo(savedUser);
        assertThat(savedOrder.getOrderedProducts().size()).isEqualTo(2);
        assertThat(savedOrder.getStatus()).isEqualTo("PROCESSING");
    }
    @Test
    @DisplayName("Тест по поиску всех пользователей")
    public void givenThreeUsers_whenFindAll_thenFindAllUsersReturned() {
        //given
        User user = DataUtils.getAntonByTransient();
        User user1 = DataUtils.getFredByTransient();
        userRepository.saveAll(List.of(user,user1));
        //then
        List<User> users = userRepository.findAll();
        //when
        assertThat(CollectionUtils.isEmpty(users)).isFalse();
    }
    @Test
    @DisplayName("Тест по поиску пользователя по email")
    public void givenUsersSaved_whenGetByEmaol_thenReturnUser() {
        //give
        User user = DataUtils.getAntonByTransient();
        userRepository.save(user);
        //when
        User userEmail= userRepository.findByEmail(user.getEmail()).orElse(null);
        //then
        assertThat(userEmail).isNotNull();
        assertThat(userEmail.getEmail()).isEqualTo(user.getEmail());
    }
    @Test
    @DisplayName("Тест обновления поля активности пользователя")
    public void givenUser_whenUpdateEnabledById_thenEnabledIsUpdated() {

        User user = DataUtils.getAntonByTransient();
        userRepository.save(user); // Сохраняем пользователя
        Long userId = user.getId();
        assertThat(userId).isNotNull(); // Проверяем, что ID не null

        Boolean newEnabledStatus = true; // Активировать пользователя

        // when
        int updatedRows = userRepository.updateEnabledById(userId, newEnabledStatus);

        // then
        assertThat(updatedRows).isEqualTo(1);

        // Проверяем значение enabled в базе данных
        Boolean enabledInDb = userRepository.getEnabledStatusById(userId);
        assertThat(enabledInDb).isEqualTo(newEnabledStatus);
    }


    @Test
    @DisplayName("Тест обновления поля активности для несуществующего пользователя")
    public void givenNonExistentUserId_whenUpdateEnabledById_thenNoRowsUpdated() {
        // given
        Long nonExistentUserId = 999L;
        Boolean newEnabledStatus = true;

        // when
        int updatedRows = userRepository.updateEnabledById(nonExistentUserId, newEnabledStatus);

        // then
        assertThat(updatedRows).isEqualTo(0);
    }
    @Test
    @DisplayName("Тест сохранения категории")
    public void givenCategory_whenSave_thenCategoryIsSaved() {
        // when
        Category savedCategory = categoryRepository.save(category);

        // then
        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId()).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("Test Category");
        assertThat(savedCategory.getProducts()).hasSize(1);
    }
    @Test
    @DisplayName("Тест поиска категории по ID")
    public void givenCategorySaved_whenFindById_thenCategoryIsFound() {
        // given
        Category savedCategory = categoryRepository.save(category);

        // when
        Category foundCategory = categoryRepository.findById(savedCategory.getId()).orElse(null);

        // then
        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getId()).isEqualTo(savedCategory.getId());
        assertThat(foundCategory.getName()).isEqualTo("Test Category");
    }

    @Test
    @DisplayName("Тест удаления категории")
    public void givenCategorySaved_whenDelete_thenCategoryIsDeleted() {
        // given
        Category savedCategory = categoryRepository.save(category);

        // when
        categoryRepository.delete(savedCategory);

        // then
        Category deletedCategory = categoryRepository.findById(savedCategory.getId()).orElse(null);
        assertThat(deletedCategory).isNull();
    }

    @Test
    @DisplayName("Тест поиска всех категорий")
    public void givenCategoriesSaved_whenFindAll_thenAllCategoriesAreFound() {
        // given
        categoryRepository.save(category);
        categoryRepository.save(Category.builder()
                .name("Все категории")
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .build());

        // when
        List<Category> categories = categoryRepository.findAll();

        // then
        assertThat(categories).hasSize(3);
    }
    @Test
    @DisplayName("Тест поиска BucketProduct по ID продукта")
    public void givenBucketProductSaved_whenFindByProductId_thenBucketProductIsFound() {
        // when
        List<BucketProduct> foundBucketProducts = bucketProductRepository.findByProductId(product.getId());

        // then
        assertThat(foundBucketProducts).isNotEmpty();
        assertThat(foundBucketProducts).hasSize(1);
        assertThat(foundBucketProducts.get(0).getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    @DisplayName("Тест обновления количества в BucketProduct")
    public void givenBucketProductSaved_whenUpdateCountById_thenCountIsUpdated() {
        // given
        BucketProduct savedBucketProduct = bucketProductRepository.save(bucketProduct);
        Integer newCount = 10;

        // when
        bucketProductRepository.updateCountById(savedBucketProduct.getId(), newCount);

        // then
        // Извлекаем обновлённый объект из базы данных
        BucketProduct updatedBucketProduct = bucketProductRepository.findById(savedBucketProduct.getId()).orElseThrow();
        assertThat(updatedBucketProduct).isNotNull();
    }

}


