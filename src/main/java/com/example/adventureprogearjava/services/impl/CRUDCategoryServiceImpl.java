package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.CategoryDTO;
import com.example.adventureprogearjava.dto.SubSubCategoryDTO;
import com.example.adventureprogearjava.dto.SubcategoryDTO;
import com.example.adventureprogearjava.entity.Category;
import com.example.adventureprogearjava.exceptions.NoContentException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.mapper.CategoryMapper;
import com.example.adventureprogearjava.repositories.CategoryRepository;
import com.example.adventureprogearjava.repositories.SectionRepository;
import com.example.adventureprogearjava.services.CRUDService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CRUDCategoryServiceImpl implements CRUDService<CategoryDTO> {
    private final static String api = "https://prime-tax-production.up.railway.app/api/public/categories/";
    private final  SectionRepository sectionRepository;

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> getAll() {
        log.info("Getting all categories");

        List<CategoryDTO> categoryDTOS = categoryRepository.findAll()
                .stream()
                .filter(category -> category.getParentCategory() == null)
                .map(categoryMapper::toDTO)
                .toList();

        categoryDTOS.forEach(categoryDTO -> {
            List<Category> subCategories = categoryRepository.getAllSubCategories(categoryDTO.getId());
            List<SubcategoryDTO> subcategoryDTOS = subCategories.stream()
                    .map(categoryMapper::toDTOFromCategory)
                    .collect(Collectors.toList());

            subcategoryDTOS.forEach(subcategoryDTO -> {
                List<Category> subSubCategories = categoryRepository.getAllSubSubCategories(subcategoryDTO.getId());
                List<SubSubCategoryDTO> subSubCategoryDTOS = subSubCategories.stream()
                        .map(categoryMapper::toSubSubCategoryDTO)
                        .collect(Collectors.toList());
                subcategoryDTO.setSubSubCategories(subSubCategoryDTOS);
            });

            categoryDTO.setSubcategories(subcategoryDTOS);
            categoryDTO.setSelfLink(api + categoryDTO.getId());
        });
        return categoryDTOS;
    }

    @Override
    public CategoryDTO getById(Long id) {
        log.info("Getting category by id");

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));


        CategoryDTO categoryDTO = categoryMapper.toDTO(category);

        List<Category> subCategories = categoryRepository.getAllSubCategories(categoryDTO.getId());
        List<SubcategoryDTO> subcategoryDTOS = subCategories.stream()
                .map(categoryMapper::toDTOFromCategory)
                .collect(Collectors.toList());

        subcategoryDTOS.forEach(subcategoryDTO -> {
            List<Category> subSubCategories = categoryRepository.getAllSubSubCategories(subcategoryDTO.getId());
            List<SubSubCategoryDTO> subSubCategoryDTOS = subSubCategories.stream()
                    .map(categoryMapper::toSubSubCategoryDTO)
                    .collect(Collectors.toList());

            subcategoryDTO.setSubSubCategories(subSubCategoryDTOS);
        });

        categoryDTO.setSubcategories(subcategoryDTOS);

        return categoryDTO;
    }


    @Override
    @Transactional
    public CategoryDTO create(CategoryDTO categoryDTO) {
        log.info("Creating new category.");
        categoryRepository.insertCategory(categoryDTO.getCategoryNameEn(), categoryDTO.getCategoryNameUa());
        return categoryDTO;
    }

    @Override
    @Transactional
    public void update(CategoryDTO categoryDTO, Long id) {
        log.info("Updating category by id");

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

        category.setCategoryNameEn(categoryDTO.getCategoryNameEn());
        category.setCategoryNameUa(categoryDTO.getCategoryNameUa());

        if (categoryDTO.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(categoryDTO.getParentCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
            category.setParentCategory(parentCategory);
        }

        categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting category by id");

        if (!categoryRepository.existsById(id)) {
            log.warn("No content present!");
            throw new NoContentException("No content present!");
        }
        categoryRepository.deleteById(id);
    }
}
