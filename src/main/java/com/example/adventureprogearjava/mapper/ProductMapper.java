package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.*;
import com.example.adventureprogearjava.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {
    String PRODUCT_API = "https://adventure-9crt.onrender.com/api/public/products/";
    String CATEGORY_API = "https://adventure-9crt.onrender.com/api/public/categories/";

    @Mapping(target = "productId", source = "id")
    @Mapping(target = "productNameUa", source = "productNameUa")
    @Mapping(target = "productNameEn", source = "productNameEn")
    @Mapping(target = "descriptionUa", source = "descriptionUa")
    @Mapping(target = "descriptionEn", source = "descriptionEn")
    @Mapping(target = "basePrice", source = "basePrice")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "averageRating", source = "averageRating")
    @Mapping(target = "reviewCount", source = "reviewCount")
    @Mapping(target = "category", source = "category", qualifiedByName = "mapToCategoryDto")
    @Mapping(target = "attributes", source = "attributes")
    @Mapping(target = "contents", source = "contents")
    @Mapping(target = "selfLink", source = "id", qualifiedByName = "idToProductLink")
    @Mapping(target = "characteristics", source = "productCharacteristics", qualifiedByName = "mapToProductCharacteristicDtos")
    ProductDTO toDto(Product product);

    @Mapping(target = "productNameUa", source = "dto.productNameUa")
    @Mapping(target = "productNameEn", source = "dto.productNameEn")
    @Mapping(target = "descriptionUa", source = "dto.descriptionUa")
    @Mapping(target = "descriptionEn", source = "dto.descriptionEn")
    @Mapping(target = "basePrice", source = "dto.basePrice")
    @Mapping(target = "gender", source = "dto.gender")
    @Mapping(target = "category", source = "dto.category", qualifiedByName = "mapToCategory")
    @Mapping(target = "attributes", source = "dto.attributes")
    @Mapping(target = "contents", source = "dto.contents")
    @Mapping(target = "productCharacteristics", source = "dto.characteristics", qualifiedByName = "mapToProductCharacteristics")
    Product toEntity(ProductDTO dto);

    @Named("mapToCategoryDto")
    @Mapping(target = "parentCategoryId", source = "parentCategory.id")
    @Mapping(target = "sectionId", source = "section.id")
    @Mapping(target = "selfLink", source = "id", qualifiedByName = "idToCategoryLink")
    @Mapping(target = "subcategories", source = "subcategories", qualifiedByName = "mapToSubcategoryDto")
    CategoryDTO mapToCategoryDto(Category category);

    @Named("mapToSubcategoryDto")
    default SubcategoryDTO mapToSubcategoryDto(Category subcategory) {
        if (subcategory == null) return null;
        SubcategoryDTO dto = new SubcategoryDTO();
        dto.setId(subcategory.getId());
        dto.setSubcategoryNameEn(subcategory.getCategoryNameEn());
        dto.setSubcategoryNameUa(subcategory.getCategoryNameUa());
        dto.setParentCategoryId(subcategory.getParentCategory() != null ? subcategory.getParentCategory().getId() : null);
        dto.setSelfLink(CATEGORY_API + subcategory.getId());
        if (subcategory.getSubcategories() != null) {
            dto.setSubSubCategories(subcategory.getSubcategories().stream()
                    .map(this::mapToSubSubCategoryDto)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    @Named("mapToSubSubCategoryDto")
    default SubSubCategoryDTO mapToSubSubCategoryDto(Category subSubCategory) {
        if (subSubCategory == null) return null;
        SubSubCategoryDTO dto = new SubSubCategoryDTO();
        dto.setId(subSubCategory.getId());
        dto.setSubSubCategoryNameEn(subSubCategory.getCategoryNameEn());
        dto.setSubSubCategoryNameUa(subSubCategory.getCategoryNameUa());
        dto.setSubCategoryId(subSubCategory.getParentCategory() != null ? subSubCategory.getParentCategory().getId() : null);
        dto.setSelfLink(CATEGORY_API + subSubCategory.getId());
        return dto;
    }

    @Named("mapToCategory")
    default Category mapToCategory(CategoryDTO categoryDTO) {
        if (categoryDTO == null) return null;
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setCategoryNameEn(categoryDTO.getCategoryNameEn());
        category.setCategoryNameUa(categoryDTO.getCategoryNameUa());
        if (categoryDTO.getSectionId() != null) {
            Section section = new Section();
            section.setId(categoryDTO.getSectionId());
            category.setSection(section);
        }
        if (categoryDTO.getParentCategoryId() != null) {
            Category parentCategory = new Category();
            parentCategory.setId(categoryDTO.getParentCategoryId());
            category.setParentCategory(parentCategory);
        }
        if (categoryDTO.getSubcategories() != null) {
            List<Category> subcategories = categoryDTO.getSubcategories().stream()
                    .map(this::mapToSubcategory)
                    .collect(Collectors.toList());
            category.setSubcategories(subcategories);
        }
        return category;
    }

    @Named("mapToSubcategory")
    default Category mapToSubcategory(SubcategoryDTO subcategoryDTO) {
        if (subcategoryDTO == null) return null;
        Category subcategory = new Category();
        subcategory.setId(subcategoryDTO.getId());
        subcategory.setCategoryNameEn(subcategoryDTO.getSubcategoryNameEn());
        subcategory.setCategoryNameUa(subcategoryDTO.getSubcategoryNameUa());
        if (subcategoryDTO.getSubSubCategories() != null) {
            List<Category> subSubCategories = subcategoryDTO.getSubSubCategories().stream()
                    .map(this::mapToSubSubCategory)
                    .collect(Collectors.toList());
            subcategory.setSubcategories(subSubCategories);
        }
        return subcategory;
    }

    @Named("mapToSubSubCategory")
    default Category mapToSubSubCategory(SubSubCategoryDTO subSubCategoryDTO) {
        if (subSubCategoryDTO == null) return null;
        Category subSubCategory = new Category();
        subSubCategory.setId(subSubCategoryDTO.getId());
        subSubCategory.setCategoryNameEn(subSubCategoryDTO.getSubSubCategoryNameEn());
        subSubCategory.setCategoryNameUa(subSubCategoryDTO.getSubSubCategoryNameUa());
        return subSubCategory;
    }

    @Named("mapToProductCharacteristicDtos")
    default List<ProductCharacteristicDTO> mapToProductCharacteristicDtos(Set<ProductCharacteristic> characteristics) { // Змінено на Set
        if (characteristics == null) return null;
        return characteristics.stream()
                .map(this::mapToProductCharacteristicDto)
                .collect(Collectors.toList());
    }

    @Named("mapToProductCharacteristicDto")
    default ProductCharacteristicDTO mapToProductCharacteristicDto(ProductCharacteristic productCharacteristic) {
        if (productCharacteristic == null) return null;
        ProductCharacteristicDTO dto = new ProductCharacteristicDTO();
        dto.setId(productCharacteristic.getId());
        dto.setValue(productCharacteristic.getValue());
        dto.setProductId(productCharacteristic.getProduct() != null ? productCharacteristic.getProduct().getId() : null);
        CategoryCharacteristic categoryCharacteristic = productCharacteristic.getCategoryCharacteristic();
        if (categoryCharacteristic != null) {
            dto.setName(categoryCharacteristic.getName());
            dto.setCategoryCharacteristicId(categoryCharacteristic.getId());
        }
        return dto;
    }

    @Named("mapToProductCharacteristics")
    default Set<ProductCharacteristic> mapToProductCharacteristics(List<ProductCharacteristicDTO> characteristicDTOs) { // Змінено на Set
        if (characteristicDTOs == null) return null;
        return characteristicDTOs.stream()
                .map(this::mapToProductCharacteristic)
                .collect(Collectors.toSet());
    }

    @Named("mapToProductCharacteristic")
    default ProductCharacteristic mapToProductCharacteristic(ProductCharacteristicDTO dto) {
        if (dto == null) return null;
        ProductCharacteristic characteristic = new ProductCharacteristic();
        characteristic.setId(dto.getId());
        characteristic.setValue(dto.getValue());
        if (dto.getCategoryCharacteristicId() != null) {
            CategoryCharacteristic categoryCharacteristic = new CategoryCharacteristic();
            categoryCharacteristic.setId(dto.getCategoryCharacteristicId());
            categoryCharacteristic.setName(dto.getName());
            characteristic.setCategoryCharacteristic(categoryCharacteristic);
        }
        if (dto.getProductId() != null) {
            Product product = new Product();
            product.setId(dto.getProductId());
            characteristic.setProduct(product);
        }
        return characteristic;
    }

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "selfLink", source = "id", qualifiedByName = "idToAttributeLink")
    ProductAttributeDTO toAttributeDto(ProductAttribute attribute);

    @Mapping(target = "id", source = "dto.id")
    ProductAttribute toEntity(ProductAttributeDTO dto);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "selfLink", source = "id", qualifiedByName = "idToContentLink")
    ContentDTO toContentDto(ProductContent content);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProduct")
    ProductContent toContentEntity(ContentDTO dto);

    @Named("mapProduct")
    default Product mapProduct(Long productId) {
        if (productId == null) return null;
        Product product = new Product();
        product.setId(productId);
        return product;
    }

    @Named("idToProductLink")
    default String idToProductLink(Long id) {
        return id != null ? PRODUCT_API + id : null;
    }

    @Named("idToCategoryLink")
    default String idToCategoryLink(Long id) {
        return id != null ? CATEGORY_API + id : null;
    }

    @Named("idToContentLink")
    default String idToContentLink(Long id) {
        return id != null ? PRODUCT_API + "contents/" + id : null;
    }

    @Named("idToAttributeLink")
    default String idToAttributeLink(Long id) {
        return id != null ? PRODUCT_API + "attributes/" + id : null;
    }

    @Named("idToLink")
    default String getLink(Long id) {
        return id != null ? PRODUCT_API + id : null;
    }
}
