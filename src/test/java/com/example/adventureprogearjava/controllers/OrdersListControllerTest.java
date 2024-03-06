package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.OrdersListDTO;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.services.impl.CRUDOrdersListServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersListControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CRUDOrdersListServiceImpl ordersListService;

    OrdersListDTO validOrderListDTO;
    OrdersListDTO invalidOrderListDTO;

    @BeforeEach
    public void setUp() {
        validOrderListDTO = OrdersListDTO.builder()
                .orderId(2L)
                .productId(3L)
                .productAttributeId(1L)
                .quantity(15L)
                .build();

        invalidOrderListDTO = OrdersListDTO.builder()
                .orderId(null)
                .productId(null)
                .productAttributeId(-3L)
                .quantity(0L)
                .build();
    }

    @Test
    public void getAllOrdersListTest() throws Exception {
        when(ordersListService.getAll()).thenReturn(Collections.singletonList(validOrderListDTO));

        mockMvc.perform(get("/api/order-lists")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].quantity", is(15)))
        ;
    }

    @Test
    public void getOrdersListByIdTest() throws Exception {
        Long orderListId = 1L;

        when(ordersListService.getById(orderListId)).thenReturn(validOrderListDTO);

        mockMvc.perform(get("/api/order-lists/" + orderListId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(validOrderListDTO)));

    }

    @Test
    public void createValidOrdersListTest() throws Exception {
        when(ordersListService.create(any(OrdersListDTO.class))).thenReturn(validOrderListDTO);

        mockMvc.perform(post("/api/order-lists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOrderListDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(validOrderListDTO)));
    }

    @Test
    public void createInvalidOrdersListTest() throws Exception {
        mockMvc.perform(post("/api/order-lists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOrderListDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateOrdersListTest() throws Exception {
        Long orderListId = 1L;

        doNothing().when(ordersListService).update(any(OrdersListDTO.class), eq(orderListId));

        mockMvc.perform(put("/api/order-lists/" + orderListId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOrderListDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteOrdersListTest() throws Exception {
        Long orderListId = 1L;

        doNothing().when(ordersListService).delete(orderListId);

        mockMvc.perform(delete("/api/order-lists/" + orderListId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteNonExistentOrdersListTest() throws Exception {
        Long nonExistentOrdersListId = -1L;

        doThrow(new ResourceNotFoundException("Order List not found with id " + nonExistentOrdersListId))
                .when(ordersListService).delete(nonExistentOrdersListId);

        mockMvc.perform(delete("/api/order-lists/" + nonExistentOrdersListId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
