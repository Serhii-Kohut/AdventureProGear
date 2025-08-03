package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.annotation.productAttributeController.CreateProductAttribute;
import com.example.adventureprogearjava.annotation.productAttributeController.DeleteProductAttributes;
import com.example.adventureprogearjava.annotation.productAttributeController.GetAllProductsAttributes;
import com.example.adventureprogearjava.annotation.productAttributeController.GetProductAttributesById;
import com.example.adventureprogearjava.annotation.productAttributeController.GetProductsByAttributeLabel;
import com.example.adventureprogearjava.annotation.productAttributeController.UpdateProductAttributes;
import com.example.adventureprogearjava.dto.ProductAttributeDTO;
import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.services.ProductAttributeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    ProductAttributeService productAttributeServiceImp;

    @GetAllProductsAttributes
    public List<ProductAttributeDTO> getAllProductAttributes() {
        return productAttributeServiceImp.getAll();
    }

    @GetProductAttributesById(path = "/{id}")
    public ProductAttributeDTO getProductAttributeById(@PathVariable Long id) {
        return productAttributeServiceImp.getById(id);
    }

    @CreateProductAttribute
    public ProductAttributeDTO createProductAttribute(@Valid @RequestBody ProductAttributeDTO productAttributeDTO) {
        return productAttributeServiceImp.create(productAttributeDTO);
    }

    @UpdateProductAttributes(path = "/{id}")
    public void updateProductAttribute(@PathVariable Long id, @RequestBody ProductAttributeDTO productAttributeDTO) {
        productAttributeServiceImp.update(productAttributeDTO, id);
    }

    @DeleteProductAttributes(path = "/{id}")
    public void deleteProductAttribute(@PathVariable Long id) {
        productAttributeServiceImp.delete(id);
    }

    @GetProductsByAttributeLabel(path = "/by-label/{label}")
    public Page<ProductDTO> getProductsByLabel(@PathVariable String label, Pageable pageable) {
        return productAttributeServiceImp.getProductsByAttributeLabel(label, pageable);
    }
}