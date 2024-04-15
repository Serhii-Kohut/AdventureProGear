package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.UserCreateDTO;
import com.example.adventureprogearjava.dto.UserDTO;
import com.example.adventureprogearjava.dto.registrationDto.UserResponseDto;
import com.example.adventureprogearjava.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);

    UserCreateDTO toDTOForCreate(User user);

    User toEntity(UserDTO dto);

    User toEntityFromCreateDto(UserCreateDTO dto);

    User userResponseDtoToUser(UserResponseDto dto);

    UserResponseDto userToUserResponseDto(User user);
}
