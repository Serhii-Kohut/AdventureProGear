package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.annotation.productContentController.*;
import com.example.adventureprogearjava.dto.ContentDTO;
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
@RequestMapping("/api/public/productContent")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "ProductContent Controller",
        description = "API operations with product content")
public class ProductContentController {
    CRUDService<ContentDTO> productContentCRUDService;

    @GetAllContents(path = "")
    public List<ContentDTO> getAllProductContent() {
        return productContentCRUDService.getAll();
    }

    @GetContentById(path = "/{id}")
    public ContentDTO getProductContentById(@PathVariable Long id) {
        return productContentCRUDService.getById(id);
    }

    @CreateContent(path = "")
    public ContentDTO createProductContent(@Valid @RequestBody ContentDTO contentDTO) {
        return productContentCRUDService.create(contentDTO);
    }

    @UpdateContent(path = "/{id}")
    public void updateProductContent(@PathVariable Long id, @Valid @RequestBody ContentDTO contentDTO) {
        productContentCRUDService.update(contentDTO, id);
    }

    @DeleteContent(path = "/{id}")
    public void deleteProductContent(@PathVariable Long id) {
        productContentCRUDService.delete(id);
    }
}
