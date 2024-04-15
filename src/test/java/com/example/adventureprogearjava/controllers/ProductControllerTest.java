package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.ProductDTO;
import com.example.adventureprogearjava.entity.Category;
import com.example.adventureprogearjava.entity.enums.Gender;
import com.example.adventureprogearjava.exceptions.NoContentException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.services.CRUDService;
import com.example.adventureprogearjava.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ProductControllerTest {
    @Mock
    private CRUDService<ProductDTO> crudService;

    @Mock
    ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductDTO productDTO;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        productDTO = ProductDTO.builder()
                .productNameEn("name")
                .descriptionEn("description")
                .productNameUa("name")
                .descriptionUa("description")
                .basePrice(100L)
                .category(Category.builder().categoryNameEn("BAGS").categoryNameUa("BAGS").build())
                .gender(Gender.MALE)
                .build();
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void getAllProducts() throws Exception {
        when(crudService.getAll()).thenReturn(List.of(productDTO));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(productDTO
                        .getProductNameEn())));
    }

    @Test
    void getAllProductsWithFilter() throws Exception {
        when(productService.getProductsByCategory(any()))
                .thenReturn(List.of(productDTO));

        mockMvc.perform(get("/api/v1/products?category=lol"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(productDTO
                        .getProductNameEn())));
    }

    @Test
    void getProductById() throws Exception {
        when(crudService.getById(1L)).thenReturn(productDTO);

        when(crudService.getById(-1L))
                .thenThrow(new ResourceNotFoundException());
        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(productDTO
                        .getProductNameEn())));
        mockMvc.perform(get("/api/v1/products/-1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProductsByName() throws Exception {
        when(productService.getProductsByName(any()))
                .thenReturn(List.of(productDTO));

        mockMvc.perform(get("/api/v1/products/name/name"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(productDTO
                        .getProductNameEn())));
    }

    @Test
    void createProduct() throws Exception {
        ProductDTO newProduct = ProductDTO.builder()
                .productNameEn("name1")
                .descriptionEn("description1")
                .productNameUa("name1")
                .descriptionUa("description1")
                .basePrice(100L)
                .category(Category.builder().categoryNameEn("BAGS").categoryNameUa("BAGS").build())
                .gender(Gender.MALE)
                .build();
        String newContentJson = objectMapper.writeValueAsString(newProduct);
        when(crudService.create(any())).thenReturn(newProduct);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContentJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(newProduct
                        .getProductNameEn())));
    }

    @Test
    void createWithInvalidData() throws Exception {
        ProductDTO invalidDTO = new ProductDTO();
        String newContentJson = objectMapper.writeValueAsString(invalidDTO);
        when(crudService.create(any())).thenReturn(invalidDTO);
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContentJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProduct() throws Exception {
        ProductDTO newProduct = ProductDTO.builder()
                .productNameEn("name1")
                .descriptionEn("description1")
                .productNameUa("name1")
                .descriptionUa("description1")
                .basePrice(100L)
                .category(Category.builder().categoryNameEn("BAGS").categoryNameUa("BAGS").build())
                .gender(Gender.MALE)
                .build();
        String newContentJson = objectMapper.writeValueAsString(newProduct);
        when(crudService.create(any())).thenReturn(newProduct);

        doAnswer((Answer<Void>) invocation -> {
            throw new ResourceNotFoundException();
        }).when(crudService)
                .update(any(),eq(-1L));

        mockMvc.perform(put("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContentJson))
                .andExpect(status().isOk());
        mockMvc.perform(put("/api/v1/products/-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContentJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct() throws Exception {
        doAnswer((Answer<Void>) invocation -> {
            throw new NoContentException();
        }).when(crudService)
                .delete(-1L);
        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/api/v1/products/-1"))
                .andExpect(status().isNoContent());
    }
}