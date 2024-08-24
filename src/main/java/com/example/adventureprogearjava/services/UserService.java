package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.PasswordUpdateDTO;
import com.example.adventureprogearjava.dto.UserCreateDTO;
import com.example.adventureprogearjava.dto.UserDTO;
import com.example.adventureprogearjava.dto.UserUpdateDTO;
import com.example.adventureprogearjava.dto.registrationDto.UserEmailDto;
import com.example.adventureprogearjava.dto.registrationDto.UserRequestDto;
import com.example.adventureprogearjava.dto.registrationDto.UserResponseDto;
import com.example.adventureprogearjava.dto.registrationDto.VerificationTokenMessageDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Locale;

public interface UserService {
    List<UserDTO> getAll();

    UserResponseDto getUserByEmail(String email);

    UserDTO getById(Long id);

    UserCreateDTO create(UserCreateDTO userCreateDTO);

    UserResponseDto saveRegisteredUser(UserRequestDto registrationDto);

    UserDTO update(UserUpdateDTO userUpdateDTO, Long id);

    void updateEmail(UserEmailDto userEmailDto, Long id, HttpServletRequest request);

    VerificationTokenMessageDto confirmUpdateEmail(String token, Locale locale);

    void updatePassword(PasswordUpdateDTO passwordUpdateDTO, Long id);

    void delete(Long id);


}
