package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.ContentDTO;
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
class CRUDProductContentServiceImplTest {
    @Autowired
    CRUDService<ContentDTO> contentService;

    @Test
    void getAll() {
        List<ContentDTO> dtos = contentService.getAll();
        assert(dtos.size()==10);
    }

    @Test
    void getById() {
        ContentDTO dtoById = contentService.getById(1L);
        assert (dtoById.getSource().equals("img1_1"));
        Exception exception = assertThrows(RuntimeException.class,
                () -> contentService.getById(11L));
        assert (exception.getMessage().equals("Resource is not available!"));
    }

    @Test
    @Sql(value = {"classpath:create_product_before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:delete_product_content_after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create() {
        ContentDTO contentDTO = new ContentDTO("img_6_1", 6L);
        contentService.create(contentDTO);
        assert(contentService.getAll().size()==11);
    }

    @Test
    @Sql(value = {"classpath:create_product_content_before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:delete_product_content_after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void update() {
        assert(contentService.getById(11L).getSource()
                .equals("img_6_1"));
      ContentDTO contentDTO = new ContentDTO("updated_img", 6L);
        contentService.update(contentDTO, 11L);
        assert (contentService.getById(11L).getSource()
                .equals(contentDTO.getSource()));
    }

    @Test
    @Sql(value = {"classpath:create_product_content_before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:delete_product_content_after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void delete() {
        assert(contentService.getAll().size()==11);
        contentService.delete(11L);
        assert(contentService.getAll().size()==10);
        Exception exception = assertThrows(RuntimeException.class,
                () -> contentService.delete(11L));
        assert (exception.getMessage().equals("No content present!"));
    }
}