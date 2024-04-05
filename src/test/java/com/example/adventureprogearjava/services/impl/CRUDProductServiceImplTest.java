package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.entity.Category;
import com.example.adventureprogearjava.entity.enums.Gender;
import com.example.adventureprogearjava.services.CRUDService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(locations= "classpath:application.yml")
class CRUDProductServiceImplTest {
    @Autowired
    CRUDService<ProductDTO> productService;

    @Test
    void getAll() {
        List<ProductDTO> dtos = productService.getAll();
        assert(dtos.size()==5);
    }

    @Test
    void getById() {
        ProductDTO dtoById = productService.getById(1L);
        assert (dtoById.getProductName().equals("T-Shirt"));
        Exception exception = assertThrows(RuntimeException.class,
                () -> productService.getById(7L));
        assert (exception.getMessage().equals("Resource is not available!"));
    }

    @Test
    @Sql(value = {"classpath:delete_product_after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create() {
        ProductDTO productDTO = ProductDTO
                .builder()
                .productName("name")
                .description("descr")
                .basePrice(100L)
                .gender(Gender.MALE)
                .category(Category.builder().categoryName("PANTS").build())
                .build();
        ProductDTO created = productService.create(productDTO);
        assert (created.getProductName().equals(productDTO.getProductName()));
        assert(productService.getAll().size()==6);
    }

    @Test
    @Sql(value = {"classpath:create_product_before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:delete_product_after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void update() {
        assert(productService.getById(6L).getProductName()
                .equals("Test"));
        ProductDTO productDTO = ProductDTO
                .builder()
                .productName("updatedName")
                .description("updatedDescr")
                .basePrice(1L)
                .category(Category.builder().categoryName("PANTS").build())
                .build();
        productService.update(productDTO, 6L);
        assert (productService.getById(6L).getProductName()
                .equals(productDTO.getProductName()));
    }


    @Test
    @Sql(value = {"classpath:create_product_before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void delete() {
        assert(productService.getAll().size()==6);
        productService.delete(6L);
        assert(productService.getAll().size()==5);
        Exception exception = assertThrows(RuntimeException.class,
                () -> productService.delete(10L));
        assert (exception.getMessage().equals("No content present!"));
    }
}