package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.annotation.productAttributeController.*;
import com.example.adventureprogearjava.dto.ProductAttributeDTO;
import com.example.adventureprogearjava.services.CRUDService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productAttributes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "ProductAttribute Controller",
        description = "API operations with product attributes")
public class ProductAttributeController {
    CRUDService<ProductAttributeDTO> productAttributeCRUDService;

    @GetAllProductsAttributes
    public List<ProductAttributeDTO> getAllProductAttributes() {
        return productAttributeCRUDService.getAll();
    }

    @GetProductAttributesById(path = "/{id}")
    public ProductAttributeDTO getProductAttributeById(@PathVariable Long id) {
        return productAttributeCRUDService.getById(id);
    }

    @CreateProductAttribute
    public ProductAttributeDTO createProductAttribute(@Valid @RequestBody ProductAttributeDTO productAttributeDTO) {
        return productAttributeCRUDService.create(productAttributeDTO);
    }

    @UpdateProductAttributes(path = "/{id}")
    public void updateProductAttribute(@PathVariable Long id, @RequestBody ProductAttributeDTO productAttributeDTO) {
        productAttributeCRUDService.update(productAttributeDTO, id);
    }

    @DeleteProductAttributes(path = "/{id}")
    public void deleteProductAttribute(@PathVariable Long id) {
        productAttributeCRUDService.delete(id);
    }
}