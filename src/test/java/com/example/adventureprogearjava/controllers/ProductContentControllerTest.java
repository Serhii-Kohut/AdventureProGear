package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.ContentDTO;
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
class ProductContentControllerTest {
    @Mock
    private CRUDService<ContentDTO> crudService;

    @InjectMocks
    private ProductContentController contentController;

    private ContentDTO contentDTO;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        contentDTO = new ContentDTO("img_1488", 1L);
        mockMvc = MockMvcBuilders.standaloneSetup(contentController).build();
    }

    @Test
    void getAllProductContent() throws Exception {
        when(crudService.getAll()).thenReturn(List.of(contentDTO));
        mockMvc.perform(get("/api/v1/productContent"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(contentDTO.getSource())));
    }

    @Test
    void getProductContentById() throws Exception {
        when(crudService.getById(1L)).thenReturn(contentDTO);
        when(crudService.getById(2L)).thenThrow(new ResourceNotFoundException());
        mockMvc.perform(get("/api/v1/productContent/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(contentDTO.getSource())));
        mockMvc.perform(get("/api/v1/productContent/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createProductContent() throws Exception {
        ContentDTO newContentDTO = new ContentDTO("new_img", 2L);
        String newContentJson = objectMapper.writeValueAsString(newContentDTO);
        when(crudService.create(any())).thenReturn(newContentDTO);
        mockMvc.perform(post("/api/v1/productContent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContentJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(newContentDTO.getSource())));
    }

    @Test
    void updateProductContent() throws Exception {
        ContentDTO newContentDTO = new ContentDTO("updated_img", 1L);
        String newContentJson = objectMapper.writeValueAsString(newContentDTO);
        doAnswer((Answer<Void>) invocation -> {
            throw new ResourceNotFoundException();
        }).when(crudService)
                .update(any(),eq(2L));
        mockMvc.perform(put("/api/v1/productContent/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContentJson))
                .andExpect(status().isOk());
        mockMvc.perform(put("/api/v1/productContent/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContentJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProductContent() throws Exception {
        doAnswer((Answer<Void>) invocation -> {
            throw new NoContentException();
        }).when(crudService)
                .delete(2L);
        mockMvc.perform(delete("/api/v1/productContent/1"))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/api/v1/productContent/2"))
                .andExpect(status().isNoContent());
    }
}