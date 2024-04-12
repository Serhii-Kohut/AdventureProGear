package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.PasswordUpdateDTO;
import com.example.adventureprogearjava.dto.UserCreateDTO;
import com.example.adventureprogearjava.dto.UserDTO;
import com.example.adventureprogearjava.dto.UserUpdateDTO;
import com.example.adventureprogearjava.dto.registrationDto.UserRequestDto;
import com.example.adventureprogearjava.dto.registrationDto.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserDTO> getAll();

    UserResponseDto getUserByEmail(String email);

    UserDTO getById(Long id);

    UserCreateDTO create(UserCreateDTO userCreateDTO);

    UserResponseDto saveRegisteredUser(UserRequestDto registrationDto);

    void update(UserUpdateDTO userUpdateDTO, Long id);

    void updatePassword(PasswordUpdateDTO passwordUpdateDTO, Long id);

    void delete(Long id);


}
