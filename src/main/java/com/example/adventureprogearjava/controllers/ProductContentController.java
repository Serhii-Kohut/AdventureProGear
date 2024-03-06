package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.ContentDTO;
import com.example.adventureprogearjava.services.CRUDService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/productContent")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductContentController {
    CRUDService<ContentDTO> productContentCRUDService;

    @GetMapping
    public List<ContentDTO> getAllProductContent() {
        return productContentCRUDService.getAll();
    }

    @GetMapping("/{id}")
    public ContentDTO getProductContentById(@PathVariable Long id) {
        return productContentCRUDService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContentDTO createProductContent(@Valid @RequestBody ContentDTO contentDTO){
        return productContentCRUDService.create(contentDTO);
    }

    @PutMapping("/{id}")
    public void updateProductContent(@PathVariable Long id, @Valid @RequestBody ContentDTO contentDTO){
        productContentCRUDService.update(contentDTO, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProductContent(@PathVariable Long id){
        productContentCRUDService.delete(id);
    }
}
