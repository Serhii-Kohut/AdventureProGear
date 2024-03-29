package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.UserDTO;
import com.example.adventureprogearjava.dto.registrationDto.UserResponseDto;
import com.example.adventureprogearjava.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    String api = "towering-house-production.up.railway.app/api/users/";

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "selfLink", source = "user.id", qualifiedByName = "idToLink")
    UserDTO toDTO(User user);

    User toEntity(UserDTO dto);

    User userResponseDtoToUser(UserResponseDto dto);

    UserResponseDto userToUserResponseDto(User user);

    @Named("idToLink")
    default String getLink(Long id) {
        return api + id;
    }
}
