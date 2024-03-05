package com.example.adventureprogearjava.controller;

import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.entity.Product;
import com.example.adventureprogearjava.services.CRUDService;
import com.example.adventureprogearjava.services.ProductService;
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
    public List<ProductDTO> getAllProducts() {
        return productCRUDService.getAll();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productCRUDService.getById(id);
    }

    @GetMapping("/filters")
    public List<ProductDTO> getProductsWithFilters() {
        //TODO implement filtration endpoint handling with gender, category and price
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO){
        return productCRUDService.create(productDTO);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        productCRUDService.update(productDTO, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productCRUDService.delete(id);
    }
}
