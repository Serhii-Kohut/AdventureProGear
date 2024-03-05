package com.example.adventureprogearjava.controller;

import com.example.adventureprogearjava.dto.ProductAttributeDTO;
import com.example.adventureprogearjava.services.CRUDService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/productAttributes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductAttributeController {
    CRUDService<ProductAttributeDTO> productAttributeCRUDService;

    @GetMapping
    public List<ProductAttributeDTO> getAllProductAttributes() {
        return productAttributeCRUDService.getAll();
    }

    @GetMapping("/{id}")
    public ProductAttributeDTO getProductAttributeById(@PathVariable Long id) {
        return productAttributeCRUDService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductAttributeDTO createProductAttribute(@Valid  @RequestBody ProductAttributeDTO productAttributeDTO){
        return productAttributeCRUDService.create(productAttributeDTO);
    }

    @PutMapping("/{id}")
    public void updateProductAttribute(@PathVariable Long id, @Valid @RequestBody ProductAttributeDTO productAttributeDTO){
        productAttributeCRUDService.update(productAttributeDTO, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProductAttribute(@PathVariable Long id){
        productAttributeCRUDService.delete(id);
    }
}
