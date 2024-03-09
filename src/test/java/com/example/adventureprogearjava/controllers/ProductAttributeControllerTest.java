package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.ProductAttributeDTO;
import com.example.adventureprogearjava.exceptions.NoContentException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.services.CRUDService;
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
class ProductAttributeControllerTest {
    @Mock
    private CRUDService<ProductAttributeDTO> crudService;

    @InjectMocks
    private ProductAttributeController attributeController;

    private ProductAttributeDTO attributeDTO;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        attributeDTO = ProductAttributeDTO.builder()
                .quantity(10L)
                .color("red")
                .size("L")
                .productId(1L)
                .priceDeviation(0L)
                .build();
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(attributeController).build();
    }

    @Test
    void getAllProductAttributes() throws Exception {
        when(crudService.getAll()).thenReturn(List.of(attributeDTO));
        mockMvc.perform(get("/api/v1/productAttributes"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(attributeDTO
                        .getProductId().toString())));
    }

    @Test
    void getProductAttributeById() throws Exception {
        when(crudService.getById(1L)).thenReturn(attributeDTO);
        when(crudService.getById(2L)).thenThrow(new ResourceNotFoundException());
        mockMvc.perform(get("/api/v1/productAttributes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(attributeDTO
                        .getProductId().toString())));
        mockMvc.perform(get("/api/v1/productAttributes/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createProductAttribute() throws Exception {
        ProductAttributeDTO newAttributeDTO = ProductAttributeDTO.builder()
                .productId(2L)
                .quantity(0L)
                .priceDeviation(-10L)
                .additional("test")
                .build();
        String newContentJson = objectMapper.writeValueAsString(newAttributeDTO);
        when(crudService.create(any())).thenReturn(newAttributeDTO);
        mockMvc.perform(post("/api/v1/productAttributes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContentJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(newAttributeDTO.getAdditional())));
    }

    @Test
    void createWithInvalidData() throws Exception {
        ProductAttributeDTO invalidDTO = ProductAttributeDTO.builder()
                .build();
        String newContentJson = objectMapper.writeValueAsString(invalidDTO);
        when(crudService.create(any())).thenReturn(invalidDTO);
        mockMvc.perform(post("/api/v1/productAttributes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContentJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProductAttribute() throws Exception {
        ProductAttributeDTO newAttributeDTO = ProductAttributeDTO.builder()
                .productId(2L)
                .quantity(0L)
                .priceDeviation(-10L)
                .additional("test")
                .build();
        String newContentJson = objectMapper.writeValueAsString(newAttributeDTO);
        doAnswer((Answer<Void>) invocation -> {
            throw new ResourceNotFoundException();
        }).when(crudService)
                .update(any(),eq(2L));
        mockMvc.perform(put("/api/v1/productAttributes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContentJson))
                .andExpect(status().isOk());
        mockMvc.perform(put("/api/v1/productAttributes/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContentJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProductAttribute() throws Exception {
        doAnswer((Answer<Void>) invocation -> {
            throw new NoContentException();
        }).when(crudService)
                .delete(2L);
        mockMvc.perform(delete("/api/v1/productAttributes/1"))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/api/v1/productAttributes/2"))
                .andExpect(status().isNoContent());
    }
}