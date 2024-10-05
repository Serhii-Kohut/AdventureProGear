package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.CategoryDTO;
import com.example.adventureprogearjava.dto.SubSubCategoryDTO;
import com.example.adventureprogearjava.dto.SubcategoryDTO;
import com.example.adventureprogearjava.entity.Category;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.mapper.CategoryMapper;
import com.example.adventureprogearjava.repositories.CategoryRepository;
import com.example.adventureprogearjava.repositories.SectionRepository;
import com.example.adventureprogearjava.services.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    private final static String api = "https://prime-tax-production.up.railway.app/api/public/categories/";

    SectionRepository sectionRepository;
    CategoryRepository categoryRepository;
    CategoryMapper mapper;

    @Override
    public CategoryDTO getCategoryByName(String name) {
        Optional<Category> category = categoryRepository.getCategoryByCategoryNameEn(name);
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("Resource is not available!");
        }

        CategoryDTO categoryDTO = category.map(mapper::toDTO).get();
        List<Category> subCategories = categoryRepository.getAllSubCategories(categoryDTO.getId());
        categoryDTO.setSubcategories(mapper.toSubcategoryDTOs(subCategories));
        categoryDTO.setSelfLink(api + categoryDTO.getId());

        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> getAllCategoriesBySection(Long id) {
        List<CategoryDTO> categoryDTOS = categoryRepository.getAllCategoriesBySection(id)
                .stream()
                .filter(category -> category.getParentCategory() == null)
                .map(mapper::toDTO)
                .toList();

        categoryDTOS.forEach(categoryDTO -> {
            List<Category> subCategories = categoryRepository.getAllSubCategories(categoryDTO.getId());
            List<SubcategoryDTO> subcategoryDTOS = subCategories.stream()
                    .map(mapper::toDTOFromCategory)
                    .toList();

            categoryDTO.setSubcategories(subcategoryDTOS);
            categoryDTO.setSelfLink(api + categoryDTO.getId());
        });

        return categoryDTOS;
    }

    @Override
    public List<CategoryDTO> getAllSubCategories(Long parentId) {
        List<CategoryDTO> subCategoryList = categoryRepository
                .getAllSubCategories(parentId)
                .stream()
                .map(mapper::toDTO)
                .toList();

        if (!categoryRepository.existsById(parentId)) {
            throw new ResourceNotFoundException("Cannot find category with id: " + parentId);
        }

        subCategoryList.forEach(categoryDTO -> categoryDTO.setSelfLink(api + categoryDTO.getId()));
        return subCategoryList;
    }

    @Override
    public List<CategoryDTO> getAllSubSubCategories(Long parentCategoryId) {
        List<CategoryDTO> subSubCategoryList = categoryRepository
                .getAllSubCategories(parentCategoryId)
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
        if (!sectionRepository.existsById(sectionId)) {
            throw new IllegalArgumentException("Section with ID " + sectionId + " does not exist.");
        }

        if (categoryRepository.findByCategoryNameUa(categoryDTO.getCategoryNameUa()).isPresent()) {
            throw new DataIntegrityViolationException("Category with name '" + categoryDTO.getCategoryNameUa() + "' already exists.");
        }

        Category category = Category.builder()
                .categoryNameEn(categoryDTO.getCategoryNameEn())
                .categoryNameUa(categoryDTO.getCategoryNameUa())
                .section(sectionRepository.findById(sectionId).orElseThrow())
                .build();

        categoryRepository.save(category);

        return mapper.toDTO(category);
    }

    @Transactional
    @Override
    public SubcategoryDTO createSubcategory(Long parentCategoryId, SubcategoryDTO categoryDTO) {
        Category parentCategory = categoryRepository.findById(parentCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find parent category with id: " + parentCategoryId));

        Category subCategory = Category.builder()
                .categoryNameEn(categoryDTO.getSubcategoryNameEn())
                .categoryNameUa(categoryDTO.getSubcategoryNameUa())
                .parentCategory(parentCategory)
                .section(parentCategory.getSection())
                .build();

        categoryRepository.save(subCategory);

        return mapper.toDTOFromCategory(subCategory);
    }

    @Transactional
    @Override
    public SubSubCategoryDTO createSubSubCategory(Long subcategoryId, SubSubCategoryDTO subSubCategoryDTO) {
        Category subCategory = categoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with ID: " + subcategoryId));

        Category subSubCategory = Category.builder()
                .categoryNameEn(subSubCategoryDTO.getSubSubCategoryNameEn())
                .categoryNameUa(subSubCategoryDTO.getSubSubCategoryNameUa())
                .parentCategory(subCategory)
                .section(subCategory.getSection())
                .build();

        categoryRepository.save(subSubCategory);

        return mapper.toSubSubCategoryDTO(subSubCategory);
    }

    @Transactional
    @Override
    public SubcategoryDTO updateSubcategory(Long id, SubcategoryDTO subcategoryDTO) {
        Category subcategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with ID: " + id));

        if (subcategoryDTO.getSubcategoryNameEn() != null) {
            subcategory.setCategoryNameEn(subcategoryDTO.getSubcategoryNameEn());
        }
        if (subcategoryDTO.getSubcategoryNameUa() != null) {
            subcategory.setCategoryNameUa(subcategoryDTO.getSubcategoryNameUa());
        }

        categoryRepository.save(subcategory);
        return mapper.toDTOFromCategory(subcategory);
    }

    @Transactional
    @Override
    public SubSubCategoryDTO updateSubSubcategory(Long id, SubSubCategoryDTO subSubCategoryDTO) {
        Category subSubcategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sub-subcategory not found with ID: " + id));

        if (subSubCategoryDTO.getSubSubCategoryNameEn() != null) {
            subSubcategory.setCategoryNameEn(subSubCategoryDTO.getSubSubCategoryNameEn());
        }
        if (subSubCategoryDTO.getSubSubCategoryNameUa() != null) {
            subSubcategory.setCategoryNameUa(subSubCategoryDTO.getSubSubCategoryNameUa());
        }

        categoryRepository.save(subSubcategory);
        return mapper.toSubSubCategoryDTO(subSubcategory);
    }

    @Transactional
    @Override
    public void deleteSubcategory(Long id) {
        Category subcategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with ID: " + id));

        categoryRepository.delete(subcategory);
    }

    @Transactional
    @Override
    public void deleteSubSubcategory(Long id) {
        Category subSubcategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sub-subcategory not found with ID: " + id));

        categoryRepository.delete(subSubcategory);
    }

    @Override
    public SubSubCategoryDTO getSubSubCategoryById(Long id) {
        Category subSubCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sub-subcategory not found with ID: " + id));
        return mapper.toSubSubCategoryDTO(subSubCategory);
    }
}
