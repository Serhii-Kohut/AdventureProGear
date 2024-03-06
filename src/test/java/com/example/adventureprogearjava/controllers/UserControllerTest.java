package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.UserDTO;
import com.example.adventureprogearjava.entity.enums.Role;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.services.impl.CRUDUserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CRUDUserServiceImpl crudUserService;

    UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO("John", "Doe", "john.doe@example.com",
                "1234567890", true, LocalDate.now(), Role.USER);
    }

    @Test
    public void getAllUsersTest() throws Exception {
        List<UserDTO> users = Collections.singletonList(userDTO);

        when(crudUserService.getAll()).thenReturn(users);

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(userDTO.getName())));
    }

    @Test
    public void getUserByIdTest() throws Exception {
        Long userId = 1L;

        when(crudUserService.getById(userId)).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDTO)));
    }

    @Test
    public void getUserWithInvalidIdTest() throws Exception {
        Long invalidUserId = -1L;

        when(crudUserService.getById(invalidUserId))
                .thenThrow(new ResourceNotFoundException("User not found with id " + invalidUserId));

        mockMvc.perform(get("/api/users/" + invalidUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void createUserTest() throws Exception {
        when(crudUserService.create(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(userDTO.getName())));
    }

    @Test
    public void createUserWithInvalidDataTest() throws Exception {
        UserDTO userDTOBad = new UserDTO("John", "Doe", "invalid email",
                "1234567890", true, LocalDate.now(), Role.USER);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTOBad)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void updateUserTest() throws Exception {
        Long userId = 1L;

        doNothing().when(crudUserService).update(any(UserDTO.class), eq(userId));

        mockMvc.perform(put("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserWithInvalidIdTest() throws Exception {
        Long invalidUserId = -1L;

        doThrow(new ResourceNotFoundException("User not found with id " + invalidUserId))
                .when(crudUserService).update(any(UserDTO.class), eq(invalidUserId));

        mockMvc.perform(put("/api/users/" + invalidUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isNotFound());
    }


    @Test
    public void deleteUserTest() throws Exception {
        Long nonExistentUserId = -1L;

        doThrow(new ResourceNotFoundException("User not found with id " + nonExistentUserId))
                .when(crudUserService).delete(nonExistentUserId);

        mockMvc.perform(delete("/api/users/" + nonExistentUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
