package ru.Sber.SberDiplomaPaper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.Sber.SberDiplomaPaper.domain.dto.bucketorder.BucketProductCreationDto;
import ru.Sber.SberDiplomaPaper.domain.dto.bucketorder.BucketProductDto;
import ru.Sber.SberDiplomaPaper.domain.dto.bucketorder.BucketProductUpdateCountDto;
import ru.Sber.SberDiplomaPaper.domain.model.BucketProduct;
import ru.Sber.SberDiplomaPaper.domain.util.DtoConverter;
import ru.Sber.SberDiplomaPaper.service.bucketProduct.BucketProductService;

import java.util.List;

@RestController
@RequestMapping("/api/bucketProducts")
@RequiredArgsConstructor
public class BucketProductController {
    private final DtoConverter dtoConverter;
    private final BucketProductService bucketProductImpl;


    @PostMapping("/add")
    public BucketProductDto addProductToBucket(
            @RequestBody BucketProductCreationDto dto) {
        BucketProduct bucketProduct = bucketProductImpl.addProduct(dto.getProductId(), dto.getUserId(), dto.getCount());
        return dtoConverter.toDto(bucketProduct, BucketProductDto.class);
    }

    @PutMapping("/update/{id}")
    public BucketProductDto updateProductCount(
            @RequestBody BucketProductUpdateCountDto dto) {
        BucketProduct updatedBucketProduct = bucketProductImpl.updateCount(dto.getId(), dto.getCount());
        return dtoConverter.toDto(updatedBucketProduct, BucketProductDto.class);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public void deleteProductFromBucket(@PathVariable Long id) {
        bucketProductImpl.delete(id);
    }

    @GetMapping("/list/{userId}")
    public List<BucketProductDto> listBucketOrders(@PathVariable Long userId) {
        List<BucketProduct> bucketProducts = bucketProductImpl.findByUserId(userId);
        return dtoConverter.toDto(bucketProducts, BucketProductDto.class);
    }
}