package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.OrdersListDTO;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.services.CRUDService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
public class OrdersListServiceImplTest {
    @Autowired
    CRUDService<OrdersListDTO> orderListService;

    @Test
    void getAll() {
        List<OrdersListDTO> dtos = orderListService.getAll();
        assert (!dtos.isEmpty());
    }

    @Test
    void getById() {
        OrdersListDTO dtoById = orderListService.getById(1L);
        assert (dtoById != null);

        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> orderListService.getById(9999L));
        assert (exception.getMessage().contains("Orders list not found with id"));
    }

    @Test
    @Sql(scripts = {"classpath:create_orders_list_before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:delete_orders_list_after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create() {

        OrdersListDTO ordersListDTO = OrdersListDTO.builder()
                .orderId(11L)
                .productId(22L)
                .productAttributeId(55L)
                .quantity(2L)
                .build();

        OrdersListDTO createdOrdersList = orderListService.create(ordersListDTO);

        assert(createdOrdersList != null);
        assert(createdOrdersList.getOrderId().equals(ordersListDTO.getOrderId()));
    }





}
