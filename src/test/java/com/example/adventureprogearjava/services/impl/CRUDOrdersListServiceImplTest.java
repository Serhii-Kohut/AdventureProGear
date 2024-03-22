package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.OrdersListDTO;
import com.example.adventureprogearjava.entity.OrdersList;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.repositories.OrdersListRepository;
import com.example.adventureprogearjava.services.CRUDService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CRUDOrdersListServiceImplTest {
    @Autowired
    CRUDService<OrdersListDTO> orderListService;

    @Autowired
    OrdersListRepository ordersListRepository;

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
    @Transactional
    @Sql(scripts = {"classpath:create_orders_list_before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "SELECT setval('orders_list_seq', (SELECT MAX(id) FROM test.public.orders_list))",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:delete_orders_list_after_create.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create() {

        OrdersListDTO ordersListDTO = OrdersListDTO.builder()
                .orderId(2L)
                .productId(2L)
                .productAttributeId(5L)
                .quantity(2L)
                .build();

        OrdersListDTO createdOrdersList = orderListService.create(ordersListDTO);

        assert (createdOrdersList != null);
        assert (createdOrdersList.getOrderId().equals(ordersListDTO.getOrderId()));
    }

    @Test
    @Sql(scripts = {"classpath:create_orders_list_before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:delete_orders_list_after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    void update() {
        OrdersListDTO ordersListDTO = OrdersListDTO.builder()
                .orderId(2L)
                .productId(3L)
                .productAttributeId(4L)
                .quantity(2L)
                .build();

        orderListService.update(ordersListDTO, 1L);

        OrdersList updatedOrdersList = ordersListRepository.findById(1L).orElse(null);

        assert (updatedOrdersList != null);
        assert (updatedOrdersList.getOrder().getId().equals(ordersListDTO.getOrderId()));
    }

    @Test
    @Sql(value = {"classpath:create_orders_list_before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:delete_orders_list_after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    void delete() {
        assert (orderListService.getAll().size() == 4);
        orderListService.delete(2L);
        assert (orderListService.getAll().size() == 3);
        Exception exception = assertThrows(RuntimeException.class,
                () -> orderListService.delete(555L));
        assert (exception.getMessage().equals("No content present!"));
    }

}