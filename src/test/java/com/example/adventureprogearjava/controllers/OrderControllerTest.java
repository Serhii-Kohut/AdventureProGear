package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.config.JwtProperties;
import com.example.adventureprogearjava.dto.OrderDTO;
import com.example.adventureprogearjava.entity.Order;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.entity.enums.OrderStatus;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.mapper.OrderMapper;
import com.example.adventureprogearjava.repositories.OrderRepository;
import com.example.adventureprogearjava.services.impl.CRUDOrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    @Autowired
    JwtProperties jwtProperties;

    @MockBean
    CRUDOrderServiceImpl crudOrderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderMapper orderMapper;

    OrderDTO validOrderDTO;
    OrderDTO invalidOrderDTO;

    private String createMockJWT(String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("type", "test");
        claims.put("id", 1);

        return Jwts.builder()
                .setSubject("mockUser")
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration()))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecretKey())))
                .compact();
    }

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

        String jwt = createMockJWT("ADMIN");

        mockMvc.perform(get("/api/orders")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].city", is(validOrderDTO.getCity())));
    }

    @Test
    public void getOrderByIdTest() throws Exception {
        Long orderId = 1L;
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Order order = new Order();
        order.setId(orderId);
        order.setUser(user);

        OrderDTO expectedOrderDTO = validOrderDTO;

        when(orderRepository.findByIdAndUser(orderId, user)).thenReturn(Optional.of(order));
        when(orderMapper.toDTO(order)).thenReturn(expectedOrderDTO);

        String jwt = createMockJWT("User");

        mockMvc.perform(get("/api/orders/" + orderId)
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedOrderDTO)));

        verify(orderRepository, times(1)).findByIdAndUser(orderId, user);
        verify(orderMapper, times(1)).toDTO(order);
    }

    @Test
    public void createValidOrderTest() throws Exception {
        String jwt = createMockJWT("USER");

        when(crudOrderService.createOrder(any(OrderDTO.class), any(User.class))).thenReturn(validOrderDTO);

        mockMvc.perform(post("/api/orders")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOrderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city", is(validOrderDTO.getCity())));
    }

    @Test
    public void createInvalidOrderTest() throws Exception {
        String jwt = createMockJWT("USER");

        mockMvc.perform(post("/api/orders")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOrderDTO)))
                .andExpect(status().isBadRequest());
    }


    /*@Test
    public void updateOrderTest() throws Exception {
        Long orderId = 1L;
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        String jwt = createMockJWT("USER"); // Створіть макет JWT

        doNothing().when(crudOrderService).updateOrder(any(OrderDTO.class), eq(orderId), eq(user));

        mockMvc.perform(put("/api/orders/" + orderId)
                        .header("Authorization", "Bearer " + jwt) // Додайте JWT до заголовка
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOrderDTO)))
                .andExpect(status().isOk());

        verify(crudOrderService, times(1)).updateOrder(any(OrderDTO.class), eq(orderId), eq(user));
    }*/



    @Test
    public void deleteOrderTest() throws Exception {
        Long orderId = 1L;
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        String jwt = createMockJWT("USER");

        doNothing().when(crudOrderService).deleteOrder(orderId, user);

        mockMvc.perform(delete("/api/orders/" + orderId)
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(crudOrderService, times(1)).deleteOrder(orderId, user);
    }

    @Test
    public void deleteNonExistentOrderTest() throws Exception {
        Long nonExistentOrderId = -1L;
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        String jwt = createMockJWT("USER");

        doThrow(new ResourceNotFoundException("Order not found with id " + nonExistentOrderId))
                .when(crudOrderService).deleteOrder(nonExistentOrderId, user);

        mockMvc.perform(delete("/api/orders/" + nonExistentOrderId)
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(crudOrderService, times(1)).deleteOrder(nonExistentOrderId, user);
    }
}