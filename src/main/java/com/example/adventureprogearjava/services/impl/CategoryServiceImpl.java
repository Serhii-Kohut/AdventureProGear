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
        List<Category> subCategories = categoryRepository.getAllSubCategories(categoryDTO.getId());
        categoryDTO.setSubcategories(mapper.toSubcategoryDTOs(subCategories));

        categoryDTO.setSelfLink(api + categoryDTO.getId());
        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> getAllCategoriesBySection(Long id) {
        log.info("Getting all categories by Section");

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
        log.info("Getting all subcategories");
        List<CategoryDTO> subCategoryList = categoryRepository
                .getAllSubCategories(parentId)
                .stream()
                .map(mapper::toDTO)
                .toList();

        if (!categoryRepository.existsById(parentId)) {
            log.warn("Parent category not found!");
            throw new ResourceNotFoundException("Cannot find category with id: " + parentId);
        }

        subCategoryList.forEach(categoryDTO -> categoryDTO.setSelfLink(api + categoryDTO.getId()));
        return subCategoryList;
    }

    @Override
    public List<CategoryDTO> getAllSubSubCategories(Long parentCategoryId) {
        log.info("Getting all sub-subcategories");

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
        log.info("Creating Category with section");

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
    public void createSubcategory(Long parentCategoryId, SubcategoryDTO categoryDTO) {
        log.info("Creating subcategory for category with ID: {}", parentCategoryId);

        Category parentCategory = categoryRepository.findById(parentCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find parent category with id: " + parentCategoryId));

        Category subCategory = Category.builder()
                .categoryNameEn(categoryDTO.getSubcategoryNameEn())
                .categoryNameUa(categoryDTO.getSubcategoryNameUa())
                .parentCategory(parentCategory)
                .section(parentCategory.getSection())
                .build();

        categoryRepository.save(subCategory);
    }

    @Transactional
    @Override
    public void createSubSubCategory(Long subcategoryId, SubSubCategoryDTO subSubCategoryDTO) {
        log.info("Creating sub-subcategory for subcategory with ID: {}", subcategoryId);

        Category subCategory = categoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with ID: " + subcategoryId));

        Category subSubCategory = Category.builder()
                .categoryNameEn(subSubCategoryDTO.getSubSubCategoryNameEn())
                .categoryNameUa(subSubCategoryDTO.getSubSubCategoryNameUa())
                .parentCategory(subCategory)
                .section(subCategory.getSection())
                .build();

        categoryRepository.save(subSubCategory);
    }

    @Transactional
    @Override
    public void updateSubcategory(Long id, CategoryDTO categoryDTO) {
        log.info("Updating subcategory with ID: {}", id);

        Category subcategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with ID: " + id));

        subcategory.setCategoryNameEn(categoryDTO.getCategoryNameEn());
        subcategory.setCategoryNameUa(categoryDTO.getCategoryNameUa());

        categoryRepository.save(subcategory);
    }

    @Transactional
    @Override
    public void updateSubSubcategory(Long id, SubSubCategoryDTO categoryDTO) {
        log.info("Updating sub-subcategory with ID: {}", id);

        Category subSubcategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sub-subcategory not found with ID: " + id));

        subSubcategory.setCategoryNameEn(categoryDTO.getSubSubCategoryNameEn());
        subSubcategory.setCategoryNameUa(categoryDTO.getSubSubCategoryNameUa());

        categoryRepository.save(subSubcategory);
    }

    @Transactional
    @Override
    public void deleteSubcategory(Long id) {
        log.info("Deleting subcategory with ID: {}", id);

        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subcategory not found with ID: " + id);
        }

        categoryRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteSubSubcategory(Long id) {
        log.info("Deleting sub-subcategory with ID: {}", id);

        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sub-subcategory not found with ID: " + id);
        }

        categoryRepository.deleteById(id);
    }

    private CategoryDTO addLinkForSubcategory(CategoryDTO categoryDTO) {
        categoryDTO.setSelfLink(api + categoryDTO.getId());
        return categoryDTO;
    }
    @Override
    public SubSubCategoryDTO getSubSubCategoryById(Long id) {
        log.info("Получение sub-subcategory с ID: {}", id);
        Category subSubCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sub-subcategory no found with ID: " + id));
        return mapper.toSubSubCategoryDTO(subSubCategory);
    }
}
