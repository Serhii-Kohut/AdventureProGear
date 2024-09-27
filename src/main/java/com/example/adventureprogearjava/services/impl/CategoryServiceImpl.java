package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.CategoryDTO;
import com.example.adventureprogearjava.entity.Category;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.mapper.CategoryMapper;
import com.example.adventureprogearjava.repositories.CategoryRepository;
import com.example.adventureprogearjava.repositories.SectionRepository;
import com.example.adventureprogearjava.services.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    private final static String api = "https://prime-tax-production.up.railway.app/api/public/categories/";

    SectionRepository sectionRepository;

    CategoryRepository categoryRepository;

    CategoryMapper mapper;

    @Override
    public CategoryDTO getCategoryByName(String name) {
        log.info("Getting category by name");
        Optional<Category> category = categoryRepository.getCategoryByCategoryNameEn(name);
        if (category.isEmpty()) {
            log.warn("Category not found!");
            throw new ResourceNotFoundException("Resource is not available!");
        }
        CategoryDTO categoryDTO = category.map(mapper::toDTO).get();
        categoryDTO.setSubcategories(categoryRepository
                .getAllSubCategories(categoryDTO.getId())
                .stream()
                .map(mapper::toDTO)
                .toList());
        categoryDTO.setSelfLink(api + categoryDTO.getId());
        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> getAllCategoriesBySection(Long id) {
        log.info("Getting all categories by Section");
        List<CategoryDTO> categoryDTOS = categoryRepository.getAllCategoriesBySection(id)
                .stream()
                .filter(category -> category.getCategory() == null)
                .map(mapper::toDTO)
                .toList();
        categoryDTOS.forEach(categoryDTO -> {
            categoryDTO.setSubcategories(categoryRepository
                    .getAllSubCategories(categoryDTO.getId())
                    .stream()
                    .map(mapper::toDTO)
                    .map(this::addLinkForSubcategory)
                    .toList());
            categoryDTO.setSelfLink(api + categoryDTO.getId());
        });
        return categoryDTOS;
    }

    @Override
    public List<CategoryDTO> getAllSubCategories(Long id) {
        log.info("Getting all subcategories");
        List<CategoryDTO> subCategoryList = categoryRepository
                .getAllSubCategories(id)
                .stream()
                .map(mapper::toDTO)
                .toList();
        if (!categoryRepository.existsById(id)) {
            log.warn("Category not found!");
            throw new ResourceNotFoundException("Cannot found category with id: " + id);
        }
        subCategoryList.forEach(categoryDTO -> {
            categoryDTO.setSelfLink(api + categoryDTO.getId());
        });
        return subCategoryList;
    }

    @Override
    public List<CategoryDTO> getAllSubSubCategories(Long id) {
        log.info("Getting all sub-subcategories");
        List<CategoryDTO> subSubCategoryList = categoryRepository
                .getAllSubCategories(id)
                .stream()
                .flatMap(subCategory -> categoryRepository
                        .getAllSubCategories(subCategory.getId())
                        .stream()
                        .map(mapper::toDTO))
                .toList();

        subSubCategoryList.forEach(subSubCategory -> {
            subSubCategory.setSelfLink(api + subSubCategory.getId());
        });
        return subSubCategoryList;
    }

    @Transactional
    public CategoryDTO createCategoryWithSection(Long sectionId, CategoryDTO categoryDTO) {
        log.info("Creating Category with section");

        if (!sectionRepository.existsById(sectionId)) {
            throw new IllegalArgumentException("Section with ID " + sectionId + " does not exist.");
        }

        if (categoryRepository.findByCategoryNameUa(categoryDTO.getCategoryNameUa()).isPresent()) {
            throw new DataIntegrityViolationException("Category with name '" + categoryDTO.getCategoryNameUa() + "' already exists.");
        }

        Long generatedId = categoryRepository.insertCategoryWithSection(
                categoryDTO.getCategoryNameEn(),
                categoryDTO.getCategoryNameUa(),
                sectionId
        );

        categoryDTO.setId(generatedId);

        return categoryDTO;
    }

    @Override
    @Transactional
    public CategoryDTO createSubcategory(Long id, CategoryDTO categoryDTO) {
        if (!categoryRepository.existsById(id)) {
            log.warn("Category not found!");
            throw new ResourceNotFoundException("Cannot found category with id: " + id);
        } else {
            categoryRepository.insertSubCategory(categoryDTO.getCategoryNameEn(), categoryDTO.getCategoryNameUa(), id);
        }
        return categoryDTO;
    }
    @Transactional
    @Override
    public CategoryDTO createSubSubCategory(Long subcategoryId, CategoryDTO subSubCategoryDTO) {
        log.info("Creating sub-subcategory for subcategory with ID: {}", subcategoryId);

        Optional<Category> subcategory = categoryRepository.findById(subcategoryId);
        if (subcategory.isEmpty()) {
            throw new ResourceNotFoundException("Subcategory not found with ID: " + subcategoryId);
        }

        categoryRepository.insertSubSubCategory(
                subSubCategoryDTO.getCategoryNameEn(),
                subSubCategoryDTO.getCategoryNameUa(),
                subcategoryId
        );

        return subSubCategoryDTO;
    }
    private CategoryDTO addLinkForSubcategory(CategoryDTO categoryDTO) {
        categoryDTO.setSelfLink(api + categoryDTO.getId());
        return categoryDTO;
    }
}
