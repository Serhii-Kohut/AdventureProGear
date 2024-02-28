package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.UserDTO;
import com.example.adventureprogearjava.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    public UserDTO toDTO(User user);

    public User toEntity(UserDTO dto);
}
