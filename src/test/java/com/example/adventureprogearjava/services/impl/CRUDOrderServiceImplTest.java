package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.OrderDTO;
import com.example.adventureprogearjava.entity.enums.OrderStatus;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.services.CRUDService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
public class CRUDOrderServiceImplTest {
    @Autowired
    CRUDService<OrderDTO> orderService;

    @Test
    void getAll() {
        List<OrderDTO> dtos = orderService.getAll();
        assert (!dtos.isEmpty());
    }

    @Test
    void getById() {
        OrderDTO dtoById = orderService.getById(1L);
        assert (dtoById != null);

        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> orderService.getById(9999L));
        assert (exception.getMessage().contains("Order not found with id"));
    }

    @Test
    @Sql(value = {"classpath:delete_order_after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create() {
        OrderDTO orderDTO = OrderDTO
                .builder()
                .userId(1L)
                .city("city")
                .comment("comment")
                .orderDate(LocalDateTime.now())
                .postAddress("postAddress")
                .price(100L)
                .status(OrderStatus.CANCELED)
                .build();

        OrderDTO created = orderService.create(orderDTO);

        assertThat(created.getUserId()).isEqualTo(orderDTO.getUserId());
        assertThat(orderService.getAll().size()).isEqualTo(3);
    }

    @Test
    @Sql(value = {"classpath:create_order_before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:delete_order_after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void update() {
        assert(orderService.getById(3L).getCity()
                .equals("city"));
        OrderDTO orderDTO = OrderDTO
                .builder()
                .userId(1L)
                .city("UpdatedCity")
                .postAddress("UpdatedAddress")
                .comment("UpdatedComment")
                .price(200L)
                .status(OrderStatus.DELIVERED)
                .build();
        orderService.update(orderDTO, 3L);
        assert (orderService.getById(3L).getCity()
                .equals(orderDTO.getCity()));
    }

    @Test
    @Sql(value = {"classpath:create_order_before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void delete() {
        assert(orderService.getAll().size()==3);
        orderService.delete(3L);
        assert(orderService.getAll().size()==2);
        Exception exception = assertThrows(RuntimeException.class,
                () -> orderService.delete(5L));
        assert (exception.getMessage().equals("No content present!"));
    }
}
