package ru.Sber.SberDiplomaPaper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.Sber.SberDiplomaPaper.domain.dto.category.CategoryDto;
import ru.Sber.SberDiplomaPaper.domain.dto.product.ProductCategoryDto;
import ru.Sber.SberDiplomaPaper.domain.dto.product.ProductCreationDto;
import ru.Sber.SberDiplomaPaper.domain.dto.product.ProductDto;
import ru.Sber.SberDiplomaPaper.domain.exception.ValidationException;
import ru.Sber.SberDiplomaPaper.domain.model.Product;
import ru.Sber.SberDiplomaPaper.domain.util.DtoConverter;
import ru.Sber.SberDiplomaPaper.service.product.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    //CRUDL
    public static final String CREATE_PRODUCT = "/create";
    public static final String EDIT_PRODUCT = "/edit";
    public static final String DELETE_PRODUCT = "/delete/{id}";
    public static final String LIST_PRODUCT = "/list";
    public static final String LIST_PRODUCT_BY_CATEGORY = "/listByCategory/{categoryId}";
    public static final String GET_PRODUCT = "/{id}";
    public static final String ATTACH_CATEGORY = "/attachCategory";
    public static final String DETACH_CATEGORY = "/detachCategory";
    private final ProductService productService;
    private final DtoConverter dtoConverter;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(CREATE_PRODUCT)
    public ProductDto createProduct(@Validated @RequestBody ProductCreationDto productDto,
                                    BindingResult errors) {
        if (errors.hasErrors()) {
            throw new ValidationException(errors.getFieldErrors());
        }
        Product product = dtoConverter.toModel(productDto, Product.class);
        product.setId(null);
        return dtoConverter.toDto(productService.createProduct(product), ProductDto.class);
    }

    @PutMapping(EDIT_PRODUCT)
    public ProductDto editProduct(@RequestBody ProductDto productDto) {
        Product product = dtoConverter.toModel(productDto, Product.class);
        return dtoConverter.toDto(productService.updateProduct(product), ProductDto.class);
    }

    @GetMapping(GET_PRODUCT)
    public ProductDto getProduct(@PathVariable Long id) {
        return dtoConverter.toDto(productService.getProduct(id), ProductDto.class);
    }

    @GetMapping(LIST_PRODUCT)
    public List<ProductDto> listProduct(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return dtoConverter.toDto(productService.getProducts(page, size), ProductDto.class);
    }

    @GetMapping(LIST_PRODUCT_BY_CATEGORY)
    public List<ProductDto> listProductByCategory(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @PathVariable Long categoryId
    ) {
        return dtoConverter.toDto(productService.getProductsByCategory(categoryId, page, size), ProductDto.class);
    }

    @PostMapping(ATTACH_CATEGORY)
    public ProductCategoryDto attachCategory(@RequestBody ProductCategoryDto dto) {
        return productService.attachCategory(dto.getProductId(), dto.getCategoryId());
    }

    @PostMapping(DETACH_CATEGORY)
    public ProductCategoryDto detachCategory(@RequestBody ProductCategoryDto dto) {
        return productService.detachCategory(dto.getProductId(), dto.getCategoryId());
    }


    @DeleteMapping(DELETE_PRODUCT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
