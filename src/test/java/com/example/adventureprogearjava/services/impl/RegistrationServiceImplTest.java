package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.registrationDto.RegistrationDto;
import com.example.adventureprogearjava.dto.registrationDto.UserEmailDto;
import com.example.adventureprogearjava.dto.registrationDto.UserResponseDto;
import com.example.adventureprogearjava.dto.registrationDto.VerificationTokenMessageDto;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationServiceImplTest {
    @Autowired
    RegistrationServiceImpl registrationService;

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    public void registerUserTest() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("test@test.com");
        registrationDto.setName("Test");
        registrationDto.setSurname("User");
        registrationDto.setPassword("password");

        UserResponseDto userResponseDto = registrationService.registerUser(registrationDto);

        assertNotNull(userResponseDto);
        assertEquals("Test", userResponseDto.getName());
        assertEquals("User", userResponseDto.getSurname());
        assertEquals("test@test.com", userResponseDto.getEmail());

        assertTrue(userRepository.existsByEmail("test@test.com"));
    }

    @Test
    @Transactional
    public void resendVerificationEmailTest() {
        // Спочатку створюємо користувача
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("test@test.com");
        registrationDto.setName("Test");
        registrationDto.setSurname("User");
        registrationDto.setPassword("password");
        HttpServletRequest request = new MockHttpServletRequest();

        registrationService.completeRegistration(registrationDto, request);

        UserEmailDto userEmailDto = new UserEmailDto("test@test.com");

        assertDoesNotThrow(() -> registrationService.resendVerificationEmail(userEmailDto, request));
    }


    @Test
    @Transactional
    public void sendVerificationEmailTest() {
        // Спочатку створюємо користувача
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("test@test.com");
        registrationDto.setName("Test");
        registrationDto.setSurname("User");
        registrationDto.setPassword("password");
        HttpServletRequest request = new MockHttpServletRequest();

        registrationService.completeRegistration(registrationDto, request);

        UserEmailDto userEmailDto = new UserEmailDto("test@test.com");

        assertDoesNotThrow(() -> registrationService.sendVerificationEmail(request, userEmailDto));
    }

    @Test
    @Transactional
    public void completeRegistrationTest() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("test@test.com");
        registrationDto.setName("Test");
        registrationDto.setSurname("User");
        registrationDto.setPassword("password");
        HttpServletRequest request = new MockHttpServletRequest();

        UserResponseDto userResponseDto = registrationService.completeRegistration(registrationDto, request);

        assertNotNull(userResponseDto);
        assertEquals("Test", userResponseDto.getName());
        assertEquals("User", userResponseDto.getSurname());
        assertEquals("test@test.com", userResponseDto.getEmail());

        assertTrue(userRepository.existsByEmail("test@test.com"));
    }

    @Test
    @Transactional
    public void confirmUserRegistrationTest() {
        // Спочатку створюємо користувача
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("test@test.com");
        registrationDto.setName("Test");
        registrationDto.setSurname("User");
        registrationDto.setPassword("password");
        HttpServletRequest request = new MockHttpServletRequest();

        registrationService.completeRegistration(registrationDto, request);

        User user = userRepository.findByEmail("test@test.com")
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = user.getVerificationToken();

        Locale locale = Locale.getDefault();

        VerificationTokenMessageDto verificationTokenMessageDto = registrationService.confirmUserRegistration(token, locale);

        assertNotNull(verificationTokenMessageDto);
        assertEquals(HttpStatus.OK, verificationTokenMessageDto.getStatus());
        assertTrue(verificationTokenMessageDto.isSuccess());
    }

}
