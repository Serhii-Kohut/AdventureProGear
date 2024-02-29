package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.OrderDTO;
import com.example.adventureprogearjava.entity.Order;
import com.example.adventureprogearjava.mapper.OrderMapper;
import com.example.adventureprogearjava.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
public class OrderServiceImplTest {

    @Autowired
    private OrderRepository orderRepository;

    private OrderMapper orderMapper = OrderMapper.MAPPER;

    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        orderService = new OrderServiceImpl(orderRepository, orderMapper);
    }

    @Test
    public void testGetAll() {
        // Prepare data
        Order order = new Order();
        // Set properties of the order
        orderRepository.save(order);

        // Call service
        List<OrderDTO> result = orderService.getAll();

        // Verify and assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

}
