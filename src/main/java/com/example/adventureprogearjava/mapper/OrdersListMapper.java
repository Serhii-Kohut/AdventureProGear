package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.OrdersListDTO;
import com.example.adventureprogearjava.entity.OrdersList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrdersListMapper {
    OrdersListMapper MAPPER = Mappers.getMapper(OrdersListMapper.class);

    OrdersListDTO toDTO(OrdersList ordersList);

    OrdersList toEntity(OrdersListDTO ordersListDTO);

}
