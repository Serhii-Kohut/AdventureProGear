package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.CategoryDTO;
import com.example.adventureprogearjava.services.CRUDService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CRUDService<CategoryDTO> categoryCRUDService;

    @GetMapping("")
    public List<CategoryDTO> getAllCategories(){
        return categoryCRUDService.getAll();
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable("id") Long id){
        return categoryCRUDService.getById(id);
    }

    @PostMapping("")
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO){
        return categoryCRUDService.create(categoryDTO);
    }

    @PutMapping("/{id}")
    public void updateCategory(@RequestBody CategoryDTO categoryDTO,
                               @PathVariable("id") Long id){
        categoryCRUDService.update(categoryDTO, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") Long id){
        categoryCRUDService.delete(id);
    }
}
