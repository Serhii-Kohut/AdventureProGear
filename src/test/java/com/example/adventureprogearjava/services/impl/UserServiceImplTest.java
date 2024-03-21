package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.PasswordUpdateDTO;
import com.example.adventureprogearjava.dto.UserDTO;
import com.example.adventureprogearjava.dto.UserUpdateDTO;
import com.example.adventureprogearjava.dto.registrationDto.UserRequestDto;
import com.example.adventureprogearjava.dto.registrationDto.UserResponseDto;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImplTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    PasswordEncoder passwordEncoder;
    User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(11L);
        user.setName("Danylo");
        user.setSurname("Test Surname");
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("password"));
        userRepository.save(user);
    }

    @Test
    public void testGetAll() {
        List<UserDTO> result = userService.getAll();
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
    }

    @Test
    public void testGetUserByEmail() {
        UserResponseDto result = userService.getUserByEmail("test@example.com");
        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
    }

    @Test
    public void testGetById() {
        UserDTO result = userService.getById(1L);
        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
    }

    @Test
    public void testCreate() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test User 2");
        userDTO.setSurname("Best");
        userDTO.setEmail("test1@example.com");
        userDTO.setPassword("password2");

        UserDTO result = userService.create(userDTO);

        assertNotNull(result);
        assertEquals(userDTO.getName(), result.getName());
    }

    @Test
    public void testSaveRegisteredUser() {
        UserRequestDto registrationDto = new UserRequestDto();
        registrationDto.setName("New User");
        registrationDto.setSurname("Surname");
        registrationDto.setEmail("new222@example.com");
        registrationDto.setPassword("newpassword");

        UserResponseDto result = userService.saveRegisteredUser(registrationDto);

        assertNotNull(result);
        assertEquals(registrationDto.getName(), result.getName());
    }

    @Test
    public void testUpdate() {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setName("Updated Name");

        userService.update(userUpdateDTO, 1L);

        UserDTO updatedUser = userService.getById(1L);
        assertEquals(userUpdateDTO.getName(), updatedUser.getName());
    }

    @Test
    public void testUpdatePassword() {
        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();
        passwordUpdateDTO.setPassword("updatedpassword");

        userService.updatePassword(passwordUpdateDTO, 1L);

        User updatedUser = userRepository.findById(1L).orElse(null);
        assertNotNull(updatedUser);
        assertTrue(passwordEncoder.matches(passwordUpdateDTO.getPassword(), updatedUser.getPassword()));
    }

    @Test
    public void testDelete() {
        userService.delete(1L);
        assertThrows(ResourceNotFoundException.class, () -> userService.getById(11L));
    }

}
