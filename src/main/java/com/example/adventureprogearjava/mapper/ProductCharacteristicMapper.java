package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.ProductCharacteristicDTO;
import com.example.adventureprogearjava.entity.ProductCharacteristic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductCharacteristicMapper {
    @Mapping(target = "name", source = "categoryCharacteristic.name")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "categoryCharacteristicId", source = "categoryCharacteristic.id")
    ProductCharacteristicDTO toDto(ProductCharacteristic characteristic);

    @Mapping(source = "categoryCharacteristicId", target = "categoryCharacteristic.id")
    @Mapping(source = "productId", target = "product.id")
    ProductCharacteristic toEntity(ProductCharacteristicDTO characteristicDTO);
}


