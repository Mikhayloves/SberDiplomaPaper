package ru.Sber.SberDiplomaPaper.repository;

import com.github.javafaker.Beer;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.Sber.SberDiplomaPaper.domain.model.*;
import ru.Sber.SberDiplomaPaper.service.jwt.JwtService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderedProductRepository orderedProductRepository;
    private final CategoryRepository categoryRepository;
    private final BucketProductRepository bucketRepository;
    private final ConfirmTokenRepository confirmTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        JpaRepository<?, ?>[] repositories = {confirmTokenRepository, categoryRepository, productRepository, orderedProductRepository, bucketRepository, orderRepository, userRepository};
        for (JpaRepository<?, ?> repository : repositories) {
            repository.deleteAll();
        }
        User admin = new User();
        admin.setEnabled(true);
        admin.setName("admin");
        admin.setEmail("admin@admin.com");
        admin.setPasswordHash(passwordEncoder.encode("admin"));
        admin.setRole(UserRole.ADMIN);
        admin = userRepository.save(admin);
        System.err.println(jwtService.generateAccessToken(admin));

        User user = new User();
        user.setEnabled(true);
        user.setName("user");
        user.setEmail("user@user.com");
        user.setPasswordHash(passwordEncoder.encode("user"));
        user.setRole(UserRole.USER);
        user = userRepository.save(user);
        System.err.println(jwtService.generateAccessToken(user));

        Category category = new Category();
        category.setName("General");
        Category savedCategory = categoryRepository.save(category);


        Faker faker = new Faker(new Locale("eng"));
        Random random = new Random();
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Product product = new Product();
            Beer beer = faker.beer();
            product.setName(beer.name());
            product.setDescription(beer.style());
            product.setPrice((double) random.nextInt(5000) + 1000);
            products.add(product);
        }

        List<Product> savedProducts = productRepository.saveAll(products);
        savedProducts.forEach(product -> {
            productRepository.attachCategory(product.getId(), savedCategory.getId());
                });
        Order order = new Order();
        order.setStatus("created");
        order.setUser(user);
        order = orderRepository.save(order);
        List<OrderedProduct> orderedProducts = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            OrderedProduct orderedProduct = new OrderedProduct();
            orderedProduct.setProduct(savedProducts.get(i));
            orderedProduct.setOrder(order);
            orderedProduct.setQuantity(random.nextInt(3) + 2);
            orderedProducts.add(orderedProduct);
        }
        List<OrderedProduct> savedOrderedProducts = orderedProductRepository.saveAll(orderedProducts);
        order = new Order();
        order.setStatus("created");
        order.setUser(user);
        order = orderRepository.save(order);
        orderedProducts = new ArrayList<>();
        for (int i = 3; i < 8; i++) {
            OrderedProduct orderedProduct = new OrderedProduct();
            orderedProduct.setProduct(savedProducts.get(i));
            orderedProduct.setOrder(order);
            orderedProduct.setQuantity(random.nextInt(3) + 2);
            orderedProducts.add(orderedProduct);
        }
        savedOrderedProducts = orderedProductRepository.saveAll(orderedProducts);
    }
}
