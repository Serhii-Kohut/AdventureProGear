package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.registrationDto.UserEmailDto;
import com.example.adventureprogearjava.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class VerificationTokenServiceImplTest {
    @Autowired
    private UserRepository userRepository;

    @Value("${verification.token.expiration}")
    private int expiryTimeInMinutes;

    private VerificationTokenServiceImpl verificationTokenService;

    @BeforeEach
    public void setUp() {
        verificationTokenService = new VerificationTokenServiceImpl(userRepository);
        verificationTokenService.expiryTimeInMinutes = expiryTimeInMinutes;
    }

    @Test
    public void testCreateVerificationToken() {
        UserEmailDto emailDto = new UserEmailDto();
        emailDto.setEmail("len_andrey@gmail.com");
        String token = "testToken";

        assertDoesNotThrow(() -> verificationTokenService.createVerificationToken(emailDto, token));
        assertNotNull(Objects.requireNonNull(userRepository.findByEmail("len_andrey@gmail.com").orElse(null)));
    }
}

