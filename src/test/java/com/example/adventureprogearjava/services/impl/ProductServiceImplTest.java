package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.entity.enums.Gender;
import com.example.adventureprogearjava.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
class ProductServiceImplTest {
    @Autowired
    ProductService productService;

    @Test
    void getProductsByName() {
        String productName = "T-Shirt";
        List<ProductDTO> dtos = productService.getProductsByName(productName);
        assert (!dtos.isEmpty());
        dtos = dtos.stream().filter(productDTO -> !productDTO.getProductNameEn()
                .equals(productName)).toList();
        assert (dtos.isEmpty());
    }

    @Test
    void getProductsByGender() {
        String gender = "MALE";
        List<ProductDTO> dtos = productService.getProductsByGender(gender);
        assert (!dtos.isEmpty());
        dtos = dtos.stream().filter(productDTO -> !productDTO.getGender()
                .equals(Gender.valueOf(gender))).toList();
        assert (dtos.isEmpty());
    }

    @Test
    void getProductsByCategory() {
        String category = "T_SHIRTS";
        List<ProductDTO> dtos = productService.getProductsByCategory(category);
        assert (!dtos.isEmpty());
        dtos = dtos.stream().filter(productDTO -> !productDTO.getCategory()
                .getCategoryNameEn().equals(category)).toList();
        assert (dtos.isEmpty());
    }

    @Test
    void getProductsByCategoryAndGender() {
        String category = "T_SHIRTS";
        String gender = "FEMALE";
        List<ProductDTO> dtos = productService.getProductsByCategoryAndGender(category, gender);
        assert (!dtos.isEmpty());
        dtos = dtos.stream().filter(productDTO -> !productDTO.getCategory().getCategoryNameEn()
                        .equals(category))
                .filter(productDTO -> !productDTO.getGender()
                        .equals(Gender.valueOf(gender)))
                .toList();
        assert (dtos.isEmpty());
    }

    @Test
    void getProductsByPrice() {
        Long priceFrom = 150L;
        Long priceTo = 250L;
        List<ProductDTO> dtos = productService.getProductsByPrice(priceFrom, priceTo);
        assert (!dtos.isEmpty());
        dtos = dtos.stream()
                .filter(productDTO -> !(productDTO.getBasePrice() >= priceFrom &&
                        productDTO.getBasePrice() <= priceTo)).toList();
        assert (dtos.isEmpty());
    }

    @Test
    void getProductsByPriceAndGender() {
        String gender = "MALE";
        Long priceFrom = 150L;
        Long priceTo = 250L;
        List<ProductDTO> dtos = productService.getProductsByPriceAndGender(priceFrom, priceTo, gender);
        assert (!dtos.isEmpty());
        dtos = dtos.stream()
                .filter(productDTO -> !(productDTO.getBasePrice() >= priceFrom &&
                        productDTO.getBasePrice() <= priceTo))
                .filter(productDTO -> !productDTO.getGender()
                        .equals(Gender.valueOf(gender)))
                .toList();
        assert (dtos.isEmpty());
    }

    @Test
    void getProductsByPriceAndCategory() {
        String category = "T_SHIRTS";
        Long priceFrom = 150L;
        Long priceTo = 250L;
        List<ProductDTO> dtos = productService.getProductsByPriceAndCategory(priceFrom, priceTo, category);
        assert (!dtos.isEmpty());
        dtos = dtos.stream()
                .filter(productDTO -> !(productDTO.getBasePrice() >= priceFrom &&
                        productDTO.getBasePrice() <= priceTo))
                .filter(productDTO -> !productDTO.getCategory()
                        .getCategoryNameEn().equals(category))
                .toList();
        assert (dtos.isEmpty());
    }

    @Test
    void getProductsByPriceAndCategoryAndGender() {
        String category = "T_SHIRTS";
        String gender = "FEMALE";
        Long priceFrom = 150L;
        Long priceTo = 250L;
        List<ProductDTO> dtos = productService.getProductsByPriceAndCategoryAndGender(priceFrom, priceTo, category, gender);
        assert (!dtos.isEmpty());
        dtos = dtos.stream()
                .filter(productDTO -> !(productDTO.getBasePrice() >= priceFrom &&
                        productDTO.getBasePrice() <= priceTo))
                .filter(productDTO -> !productDTO.getCategory()
                        .getCategoryNameEn().equals(category))
                .filter(productDTO -> !productDTO.getGender()
                        .equals(Gender.valueOf(gender)))
                .toList();
        assert (dtos.isEmpty());
    }
}