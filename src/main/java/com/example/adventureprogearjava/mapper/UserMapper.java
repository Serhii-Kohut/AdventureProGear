package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.UserDTO;
import com.example.adventureprogearjava.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    public UserDTO toDTO(User user);

    public User toEntity(UserDTO dto);
}
