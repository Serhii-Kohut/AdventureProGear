package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.registrationDto.RegistrationDto;
import com.example.adventureprogearjava.dto.registrationDto.UserEmailDto;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.exceptions.NoUsersFoundException;
import com.example.adventureprogearjava.repositories.UserRepository;
import com.example.adventureprogearjava.services.RegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    public void registerUserTest() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("test@test.com");
        registrationDto.setName("Test");
        registrationDto.setSurname("User");
        registrationDto.setPassword("Password1@");

        mockMvc.perform(post("/api/public/registration/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Test")))
                .andExpect(jsonPath("$.surname", is("User")))
                .andExpect(jsonPath("$.email", is("test@test.com")));
    }

    @Test
    @Transactional
    public void resendVerificationEmailTest() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("test@test.com");
        registrationDto.setName("Test");
        registrationDto.setSurname("User");
        registrationDto.setPassword("Password1@");
        HttpServletRequest request = new MockHttpServletRequest();

        registrationService.completeRegistration(registrationDto, request);

        UserEmailDto userEmailDto = new UserEmailDto("test@test.com", null, null);

        mockMvc.perform(post("/api/public/registration/resend")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userEmailDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Verification email sent successfully to: test@test.com")));
    }

    @Test
    @Transactional
    public void confirmationTest() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("test@test.com");
        registrationDto.setName("Test");
        registrationDto.setSurname("User");
        registrationDto.setPassword("Password1@");
        HttpServletRequest request = new MockHttpServletRequest();

        registrationService.completeRegistration(registrationDto, request);

        User user = userRepository.findByEmail("test@test.com")
                .orElseThrow(() -> new NoUsersFoundException("User not found"));

        String token = user.getVerificationToken();

        mockMvc.perform(get("/api/public/registration/confirmation")
                        .param("token", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }

}