package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.services.CRUDService;
import com.example.adventureprogearjava.services.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    CRUDService<ProductDTO> productCRUDService;

    ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts(@RequestParam(required = false) String gender,
                                           @RequestParam(required = false) String category,
                                           @RequestParam(required = false) Long priceFrom,
                                           @RequestParam(required = false) Long priceTo) {
        if (gender != null && category != null
                && (priceFrom != null && priceTo != null))
            return productService
                    .getProductsByPriceAndCategoryAndGender(priceFrom, priceTo, category, gender);
        else if (category != null
                && (priceFrom != null && priceTo != null))
            return productService
                    .getProductsByPriceAndCategory(priceFrom, priceTo, category);
        else if (gender != null
                && (priceFrom != null && priceTo != null))
            return productService
                    .getProductsByPriceAndGender(priceFrom, priceTo, gender);
        else if(priceFrom != null && priceTo != null)
            return productService.getProductsByPrice(priceFrom, priceTo);
        else if(gender != null && category != null)
            return productService.getProductsByCategoryAndGender(category, gender);
        else if(gender != null)
            return productService.getProductsByGender(gender);
        else if(category != null)
            return productService.getProductsByCategory(category);
        return productCRUDService.getAll();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productCRUDService.getById(id);
    }

    @GetMapping("/name/{name}")
    public List<ProductDTO> getProductsByName(@PathVariable String name) {
        return productService.getProductsByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productCRUDService.create(productDTO);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        productCRUDService.update(productDTO, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productCRUDService.delete(id);
    }
}
