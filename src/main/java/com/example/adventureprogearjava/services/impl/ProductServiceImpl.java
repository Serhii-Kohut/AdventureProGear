package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.entity.enums.Gender;
import com.example.adventureprogearjava.entity.enums.ProductCategory;
import com.example.adventureprogearjava.mapper.ProductMapper;
import com.example.adventureprogearjava.repositories.ProductRepository;
import com.example.adventureprogearjava.services.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepo;

    ProductMapper mapper;

    @Override
    public List<ProductDTO> getProductsByName(String productName) {
        return null;
    }

    @Override
    public List<ProductDTO> getProductsByGender(String gender) {
        log.info("Getting products by gender");
        return productRepo.findByGender(gender)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String category) {
        log.info("Getting products by category");
        return productRepo.findByCategory(category)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDTO> getProductsByCategoryAndGender(String category, String gender) {
        log.info("Getting products by category and gender");
        return productRepo.findByCategoryAndGender(category, gender)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDTO> getProductsByPrice(Long priceFrom, Long priceTo) {
        log.info("Getting products by price");
        return productRepo.findByBasePriceBetween(priceFrom, priceTo)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDTO> getProductsByPriceAndGender(Long priceFrom, Long priceTo, String gender) {
        log.info("Getting products by price and gender");
        return productRepo.findByBasePriceBetween(priceFrom, priceTo)
                .stream()
                .filter(product -> product.getGender().equals(Gender.valueOf(gender)))
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDTO> getProductsByPriceAndCategory(Long priceFrom, Long priceTo, String category) {
        log.info("Getting products by price and category");
        return productRepo.findByBasePriceBetween(priceFrom, priceTo)
                .stream()
                .filter(product -> product.getCategory().equals(ProductCategory.valueOf(category)))
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDTO> getProductsByPriceAndCategoryAndGender(Long priceFrom, Long priceTo, String category, String gender) {
        log.info("Getting products by price and category and gender");
        return productRepo.findByBasePriceBetween(priceFrom, priceTo)
                .stream()
                .filter(product -> product.getCategory().equals(ProductCategory.valueOf(category)))
                .filter(product -> product.getGender().equals(Gender.valueOf(gender)))
                .map(mapper::toDto)
                .toList();
    }
}
