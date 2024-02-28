package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.OrderDTO;
import com.example.adventureprogearjava.dto.UserDTO;
import com.example.adventureprogearjava.entity.Order;
import com.example.adventureprogearjava.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {
    public OrderDTO toDTO(Order order);

    public Order toEntity(OrderDTO orderDTO);
}
