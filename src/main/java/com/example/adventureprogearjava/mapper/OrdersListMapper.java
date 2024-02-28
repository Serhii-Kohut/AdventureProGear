package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.OrdersListDTO;
import com.example.adventureprogearjava.entity.OrdersList;
import org.mapstruct.Mapper;

@Mapper
public interface OrdersListMapper {
    OrdersListDTO toDTO(OrdersList ordersList);

    OrdersList toEntity(OrdersListDTO ordersListDTO);

}
