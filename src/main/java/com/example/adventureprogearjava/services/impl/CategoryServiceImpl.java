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
        Optional<Category> category = categoryRepository.getCategoryByCategoryNameEnOrCategoryNameUa(name, name);
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
        categoryRepository.insertCategoryWithSection(
                categoryDTO.getCategoryNameEn(),
                categoryDTO.getCategoryNameUa(),
                sectionId
        );
        Category newCategory = categoryRepository.findByCategoryNameUa(categoryDTO.getCategoryNameUa())
                .orElseThrow(() -> new ResourceNotFoundException("Failed to create the category"));
        return mapper.toDTO(newCategory);
    }

    @Transactional
    public SubcategoryDTO createSubcategory(Long parentCategoryId, SubcategoryDTO subcategoryDTO) {
        Category parentCategory = categoryRepository.findById(parentCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent category not found with ID: " + parentCategoryId));
        if (categoryRepository.findByCategoryNameUa(subcategoryDTO.getSubcategoryNameUa()).isPresent()) {
            throw new DataIntegrityViolationException("Subcategory with this name already exists.");
        }
        categoryRepository.insertSubCategory(
                subcategoryDTO.getSubcategoryNameEn(),
                subcategoryDTO.getSubcategoryNameUa(),
                parentCategoryId,
                parentCategory.getSection().getId()
        );
        Category newSubcategory = categoryRepository.findByCategoryNameUa(subcategoryDTO.getSubcategoryNameUa())
                .orElseThrow(() -> new ResourceNotFoundException("Failed to create the subcategory"));
        return mapper.toDTOFromCategory(newSubcategory);
    }

    @Transactional
    public SubSubCategoryDTO createSubSubCategory(Long subcategoryId, SubSubCategoryDTO subSubCategoryDTO) {
        Category parentSubCategory = categoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with ID: " + subcategoryId));
        if (categoryRepository.findByCategoryNameUa(subSubCategoryDTO.getSubSubCategoryNameUa()).isPresent()) {
            throw new DataIntegrityViolationException("SubSubcategory with this name already exists.");
        }
        categoryRepository.insertSubSubCategory(
                subSubCategoryDTO.getSubSubCategoryNameEn(),
                subSubCategoryDTO.getSubSubCategoryNameUa(),
                subcategoryId,
                parentSubCategory.getSection().getId()
        );
        Category newSubSubCategory = categoryRepository.findByCategoryNameUa(subSubCategoryDTO.getSubSubCategoryNameUa())
                .orElseThrow(() -> new ResourceNotFoundException("Failed to create the sub-subcategory"));
        return mapper.toSubSubCategoryDTO(newSubSubCategory);
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
        if (subcategoryDTO.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(subcategoryDTO.getParentCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found with ID: " + subcategoryDTO.getParentCategoryId()));
            subcategory.setParentCategory(parentCategory);
        } else {
            subcategory.setParentCategory(null);
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
        if (subSubCategoryDTO.getSubCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(subSubCategoryDTO.getSubCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found with ID: " + subSubCategoryDTO.getSubCategoryId()));
            subSubcategory.setParentCategory(parentCategory);
        } else {
            subSubcategory.setParentCategory(null);
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
    public List<SubcategoryDTO> getSubcategoriesByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));

        if (category.getParentCategory() != null) {
            throw new ResourceNotFoundException("ID belongs to a subcategory, not a category: " + categoryId);
        }

        List<Category> subCategories = categoryRepository.getAllSubCategories(categoryId);

        List<SubcategoryDTO> subcategoryDTOs = subCategories.stream()
                .map(subCategory -> {
                    SubcategoryDTO dto = mapper.toDTOFromCategory(subCategory);
                    dto.setSelfLink(api + dto.getId());
                    dto.setParentCategoryId(categoryId);
                    return dto;
                })
                .toList();

        return subcategoryDTOs;
    }


    @Override
    public List<SubSubCategoryDTO> getSubSubcategoriesBySubcategoryId(Long subcategoryId) {
        Category subcategory = categoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with ID: " + subcategoryId));

        if (subcategory.getParentCategory() == null || subcategory.getParentCategory().getParentCategory() != null) {
            throw new ResourceNotFoundException("ID belongs to a category or subsubcategory, not a subcategory: " + subcategoryId);
        }

        List<Category> subSubCategories = categoryRepository.getAllSubCategories(subcategoryId);

        List<SubSubCategoryDTO> subSubcategoryDTOs = subSubCategories.stream()
                .map(mapper::toSubSubCategoryDTO)
                .peek(subSubCategoryDTO -> {
                    subSubCategoryDTO.setSelfLink(api + subSubCategoryDTO.getId());
                    subSubCategoryDTO.setParentCategoryId(subcategoryId);
                })
                .toList();

        return subSubcategoryDTOs;
    }


}
