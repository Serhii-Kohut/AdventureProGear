package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.entity.enums.Gender;
import com.example.adventureprogearjava.entity.enums.ProductCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(locations= "classpath:application.yml")
class ProductServiceImplTest {
    @Autowired
    ProductServiceImpl productService;

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
        ProductDTO productDTO = new ProductDTO("name", "descr", 100L, Gender.MALE, ProductCategory.PANTS);
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
        ProductDTO productDTO = new ProductDTO("updatedName", "updatedDescr", 1L, null, ProductCategory.PANTS);
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
    }
}