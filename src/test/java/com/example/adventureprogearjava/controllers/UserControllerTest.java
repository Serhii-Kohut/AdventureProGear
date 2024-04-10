package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.config.JwtProperties;
import com.example.adventureprogearjava.dto.PasswordUpdateDTO;
import com.example.adventureprogearjava.dto.UserDTO;
import com.example.adventureprogearjava.dto.UserUpdateDTO;
import com.example.adventureprogearjava.entity.enums.Role;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.services.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    JwtProperties jwtProperties;

    @MockBean
    UserServiceImpl crudUserService;

    UserDTO userDTO;

    private String createMockJWT(String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("type", "test");
        claims.put("id", 1);

        return Jwts.builder()
                .setSubject("mockUser")
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration()))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecretKey())))
                .compact();
    }


    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO("John", "Doe", "john.doe@example.com", "Password1@",
                "+380505556953", true, LocalDate.now(), Role.USER);
    }

    @Test
    public void getAllUsers_Unauthorized_401() throws Exception {
        this.mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getAllUsers_Forbidden_403() throws Exception {
        String jwt = createMockJWT("USER");
        this.mockMvc.perform(get("/api/users/all")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllUsers_Success_200() throws Exception {
        String jwt = createMockJWT("ADMIN");
        this.mockMvc.perform(get("/api/users/all")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUsersTest() throws Exception {
        List<UserDTO> users = Collections.singletonList(userDTO);

        when(crudUserService.getAll()).thenReturn(users);

        String jwt = createMockJWT("ADMIN"); // generate a mock JWT with role "ADMIN"

        mockMvc.perform(get("/api/users/all")
                        .header("Authorization", "Bearer " + jwt) // include the JWT in the "Authorization" header
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(userDTO.getName())));
    }


    @Test
    public void getUserTest() throws Exception {
        when(crudUserService.getById(any())).thenReturn(userDTO);

        String jwt = createMockJWT("USER"); // generate a mock JWT with role "USER"

        mockMvc.perform(get("/api/users/me")
                        .header("Authorization", "Bearer " + jwt) // include the JWT in the "Authorization" header
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(userDTO.getName())));
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
        UserDTO userDTOBad = new UserDTO("John", "Doe", "invalid email", "Password invalid",
                "1234567890", true, LocalDate.now(), Role.USER);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTOBad)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void updateUserTest() throws Exception {
        Long userId = 1L;

        doNothing().when(crudUserService).update(any(UserUpdateDTO.class), eq(userId));

        mockMvc.perform(put("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserWithInvalidIdTest() throws Exception {
        Long invalidUserId = -1L;

        doThrow(new ResourceNotFoundException("User not found with id " + invalidUserId))
                .when(crudUserService).update(any(UserUpdateDTO.class), eq(invalidUserId));

        mockMvc.perform(put("/api/users/" + invalidUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdatePassword() throws Exception {
        Long userId = 1L;
        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();
        passwordUpdateDTO.setPassword("Password1@");

        doNothing().when(crudUserService).updatePassword(any(PasswordUpdateDTO.class), eq(userId));

        mockMvc.perform(put("/api/users/" + userId + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordUpdateDTO)))
                .andExpect(status().isOk());

        verify(crudUserService, times(1)).updatePassword(passwordUpdateDTO, userId);
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
