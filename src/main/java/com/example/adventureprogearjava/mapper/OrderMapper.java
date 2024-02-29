package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.OrderDTO;
import com.example.adventureprogearjava.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    OrderDTO toDTO(Order order);

    @Mapping(target = "status", source = "orderDTO.status")
    Order toEntity(OrderDTO orderDTO);
}
