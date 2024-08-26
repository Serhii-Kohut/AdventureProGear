package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getProductsByName(String productName);

    List<ProductDTO> getProductsByGender(String gender);

    List<ProductDTO> getProductsByCategory(String category);

    List<ProductDTO> getProductsByCategoryAndGender(String category, String gender);

    List<ProductDTO> getProductsByPrice(Long priceFrom, Long priceTo);

    List<ProductDTO> getProductsByPriceAndGender(Long priceFrom, Long priceTo, String gender);

    List<ProductDTO> getProductsByPriceAndCategory(Long priceFrom, Long priceTo, String category);

    List<ProductDTO> getProductsByPriceAndCategoryAndGender(Long priceFrom, Long priceTo, String category, String gender);
    List<ProductDTO> getProductsByPriceTo(Long priceTo);

    List<ProductDTO> getProductsByPriceFrom(Long priceFrom);
    List<ProductDTO> getAllProducts(String gender, String category, Long priceFrom, Long priceTo);
}
