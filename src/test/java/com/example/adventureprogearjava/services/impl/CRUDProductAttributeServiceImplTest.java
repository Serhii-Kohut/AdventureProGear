package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ProductAttributeDTO;
import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.entity.enums.ProductCategory;
import com.example.adventureprogearjava.services.CRUDService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
class CRUDProductAttributeServiceImplTest {
    @Autowired
    CRUDService<ProductAttributeDTO> productAttributeService;

    @Test
    void getAll() {
        List<ProductAttributeDTO> dtos = productAttributeService.getAll();
        assert (dtos.size() == 18);
    }

    @Test
    void getById() {
        ProductAttributeDTO dtoById = productAttributeService.getById(1L);
        assert (dtoById.getSize().equals("S"));
        assert (dtoById.getPriceDeviation().equals(0L));
        Exception exception = assertThrows(RuntimeException.class,
                () -> productAttributeService.getById(20L));
        assert (exception.getMessage().equals("Resource is not available!"));
    }

    @Test
    @Sql(value = {"classpath:create_product_before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:delete_productattr_after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create() {
        ProductAttributeDTO productAttributeDTO = ProductAttributeDTO.builder()
                .color("red")
                .priceDeviation(0L)
                .productId(6L)
                .quantity(5L)
                .build();
        ProductAttributeDTO created = productAttributeService
                .create(productAttributeDTO);
        assert (created.getColor().equals(productAttributeDTO.getColor()));
        assert (productAttributeService.getAll().size() == 19);
    }

    @Test
    @Sql(value = {"classpath:create_productattr_before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:delete_productattr_after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void update() {
        assert (productAttributeService.getById(20L) != null);
        ProductAttributeDTO productAttributeDTO = ProductAttributeDTO.builder()
                .color("updatedColor")
                .priceDeviation(10L)
                .quantity(4L)
                .build();
        productAttributeService.update(productAttributeDTO, 20L);
        assert (productAttributeService.getById(20L).getColor()
                .equals(productAttributeDTO.getColor()));
    }

    @Test
    @Sql(value = {"classpath:create_productattr_before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:delete_productattr_after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void delete() {
        assert(productAttributeService.getAll().size()==19);
        productAttributeService.delete(20L);
        assert(productAttributeService.getAll().size()==18);
        Exception exception = assertThrows(RuntimeException.class,
                () -> productAttributeService.delete(20L));
        assert (exception.getMessage().equals("No content present!"));
    }
}