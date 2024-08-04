package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.config.JwtProperties;
import com.example.adventureprogearjava.dto.PasswordUpdateDTO;
import com.example.adventureprogearjava.dto.UserCreateDTO;
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
import org.springframework.security.test.context.support.WithMockUser;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setName(userDTO.getName());
        userCreateDTO.setSurname(userDTO.getSurname());
        userCreateDTO.setEmail(userDTO.getEmail());
        userCreateDTO.setPassword(userDTO.getPassword());
        userCreateDTO.setDate(userDTO.getDate());
        userCreateDTO.setRole(userDTO.getRole());

        when(crudUserService.create(any(UserCreateDTO.class))).thenReturn(userCreateDTO);

        String jwt = createMockJWT("ADMIN");

        mockMvc.perform(post("/api/users")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(userDTO.getName())))
                .andExpect(jsonPath("$.surname", is(userDTO.getSurname())))
                .andExpect(jsonPath("$.email", is(userDTO.getEmail())));
    }

    @Test
    public void createUserWithInvalidDataTest() throws Exception {
        UserDTO userDTOBad = new UserDTO("John", "Doe", "invalid email", "Password invalid",
                "1234567890", true, LocalDate.now(), Role.USER);
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setName(userDTOBad.getName());
        userCreateDTO.setSurname(userDTOBad.getSurname());
        userCreateDTO.setEmail(userDTOBad.getEmail());
        userCreateDTO.setPassword(userDTOBad.getPassword());
        userCreateDTO.setDate(userDTOBad.getDate());
        userCreateDTO.setRole(userDTOBad.getRole());

        when(crudUserService.create(any(UserCreateDTO.class))).thenReturn(userCreateDTO);

        String jwt = createMockJWT("ADMIN");

        mockMvc.perform(post("/api/users")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserTestWithInvalidRole() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO();

        userCreateDTO.setName(userDTO.getName());
        userCreateDTO.setSurname(userDTO.getSurname());
        userCreateDTO.setEmail(userDTO.getEmail());
        userCreateDTO.setPassword(userDTO.getPassword());

        when(crudUserService.create(any(UserCreateDTO.class))).thenReturn(userCreateDTO);

        String jwt = createMockJWT("USER");

        mockMvc.perform(post("/api/users")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateUserTest() throws Exception {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setName(userDTO.getName());
        userUpdateDTO.setSurname(userDTO.getSurname());

        doNothing().when(crudUserService).update(any(UserUpdateDTO.class), anyLong());

        String jwt = createMockJWT("USER");

        mockMvc.perform(put("/api/users/me/update")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateDTO)))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void updateUserWithInvalidIdTest() throws Exception {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setName(userDTO.getName());
        userUpdateDTO.setSurname(userDTO.getSurname());

        doThrow(new ResourceNotFoundException("User not found")).when(crudUserService).update(any(UserUpdateDTO.class), anyLong());

        String jwt = createMockJWT("USER");

        mockMvc.perform(put("/api/users/me/update")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateDTO)))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testUpdatePassword() throws Exception {
        Long userId = 1L;
        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();
        passwordUpdateDTO.setPassword("Password1@");

        doNothing().when(crudUserService).updatePassword(any(PasswordUpdateDTO.class), eq(userId));

        String jwt = createMockJWT("USER");

        mockMvc.perform(put("/api/users/me/update-password")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordUpdateDTO)))
                .andExpect(status().isOk());

        verify(crudUserService, times(1)).updatePassword(passwordUpdateDTO, userId);
    }


    @Test
    public void testDeleteUser() throws Exception {
        Long userId = 1L;

        doNothing().when(crudUserService).delete(userId);

        String jwt = createMockJWT("USER");

        mockMvc.perform(delete("/api/users")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(crudUserService, times(1)).delete(userId);
    }

}
