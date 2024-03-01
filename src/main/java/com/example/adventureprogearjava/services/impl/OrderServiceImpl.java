package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.OrderDTO;
import com.example.adventureprogearjava.entity.Order;
import com.example.adventureprogearjava.exceptions.NoContentException;
import com.example.adventureprogearjava.exceptions.NoOrdersFoundException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.mapper.OrderMapper;
import com.example.adventureprogearjava.repositories.OrderRepository;
import com.example.adventureprogearjava.services.CRUDService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements CRUDService<OrderDTO> {
    OrderRepository orderRepository;
    OrderMapper orderMapper = OrderMapper.MAPPER;

    @Override
    public List<OrderDTO> getAll() {
        log.info("Getting all orders");

        List<Order> orders = orderRepository.findAll();

        if (orders.isEmpty()) {
            throw new NoOrdersFoundException("No orders found in the database");
        }

        return orders.stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public OrderDTO getById(Long id) {
        log.info("Getting order by id: {}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Order not found with id: {}", id);
                    return new ResourceNotFoundException("Order not found with id " + id);
                });

        return orderMapper.toDTO(order);
    }

    /*@Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        log.info("Creating new order.");

        if (orderDTO == null) {
            throw new IllegalArgumentException("OrderDTO cannot be null");
        }

        Order order = orderMapper.toEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDTO(savedOrder);
    }*/

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        log.info("Creating new order.");
        insertOrder(orderDTO);
        return orderDTO;
    }

/*    @Override
    @Transactional
    public void update(OrderDTO orderDTO, Long id) {
        log.info("Updating order with id: {}", id);

        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Order not found with id: {}", id);
                    return new ResourceNotFoundException("Order not found with id " + id);
                });

        Order orderToUpdate = orderMapper.toEntity(orderDTO);

        orderToUpdate.setId(existingOrder.getId());

        orderRepository.save(orderToUpdate);

    }*/

    @Override
    @Transactional
    public void update(OrderDTO orderDTO, Long id) {
        log.info("Updating order with id: {}", id);
        if (!orderRepository.existsById(id)) {
            log.warn("Order not found!");
            throw new ResourceNotFoundException("Order not found with id " + id);
        } else {
            orderRepository.update(id, orderDTO.getCity(), orderDTO.getComment(), orderDTO.getOrderDate(),
                    orderDTO.getPostAddress(), orderDTO.getPrice(), orderDTO.getStatus().toString(), orderDTO.getUserId());
        }
    }


    @Override
    public void delete(Long id) {
        log.info("Deleting order with id: {}", id);

        if (!orderRepository.existsById(id)) {
            log.warn("No content present!");
            throw new NoContentException("No content present!");
        }

        orderRepository.deleteById(id);
    }

    private void insertOrder(OrderDTO orderDTO) {
        orderRepository.insertOrder(
                orderDTO.getCity(),
                orderDTO.getComment(),
                orderDTO.getOrderDate(),
                orderDTO.getPostAddress(),
                orderDTO.getPrice(),
                orderDTO.getStatus().toString(),
                orderDTO.getUserId()
        );
    }
}
