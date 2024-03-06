package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.OrderDTO;
import com.example.adventureprogearjava.entity.enums.OrderStatus;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.services.impl.CRUDOrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CRUDOrderServiceImpl crudOrderService;

    OrderDTO validOrderDTO;
    OrderDTO invalidOrderDTO;

    @BeforeEach
    public void setUp() {
        validOrderDTO = OrderDTO.builder()
                .userId(1L)
                .orderDate(LocalDateTime.now())
                .city("Kyiv")
                .postAddress("Some address")
                .comment("No comment")
                .price(100L)
                .status(OrderStatus.PAID)
                .ordersLists(new ArrayList<>())
                .build();

        invalidOrderDTO = OrderDTO.builder()
                .userId(null)
                .orderDate(null)
                .city("")
                .postAddress("")
                .comment("")
                .price(-1L)
                .status(null)
                .ordersLists(null)
                .build();
    }

    @Test
    public void getAllOrdersTest() throws Exception {
        when(crudOrderService.getAll()).thenReturn(Collections.singletonList(validOrderDTO));

        mockMvc.perform(get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].city", is(validOrderDTO.getCity())));
    }

    @Test
    public void getOrderByIdTest() throws Exception {
        Long orderId = 1L;

        when(crudOrderService.getById(orderId)).thenReturn(validOrderDTO);

        mockMvc.perform(get("/api/orders/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(validOrderDTO)));
    }

    @Test
    public void createValidOrderTest() throws Exception {
        when(crudOrderService.create(any(OrderDTO.class))).thenReturn(validOrderDTO);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOrderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city", is(validOrderDTO.getCity())));
    }

    @Test
    public void createInvalidOrderTest() throws Exception {
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOrderDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateOrderTest() throws Exception {
        Long orderId = 1L;

        doNothing().when(crudOrderService).update(any(OrderDTO.class), eq(orderId));

        mockMvc.perform(put("/api/orders/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOrderDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteOrderTest() throws Exception {
        Long orderId = 1L;

        doNothing().when(crudOrderService).delete(orderId);

        mockMvc.perform(delete("/api/orders/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteNonExistentOrderTest() throws Exception {
        Long nonExistentOrderId = -1L;

        doThrow(new ResourceNotFoundException("Order not found with id " + nonExistentOrderId))
                .when(crudOrderService).delete(nonExistentOrderId);

        mockMvc.perform(delete("/api/orders/" + nonExistentOrderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}