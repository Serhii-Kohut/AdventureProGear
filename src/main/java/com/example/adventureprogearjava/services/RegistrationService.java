package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.registrationDto.RegistrationDto;
import com.example.adventureprogearjava.dto.registrationDto.UserEmailDto;
import com.example.adventureprogearjava.dto.registrationDto.UserResponseDto;
import com.example.adventureprogearjava.dto.registrationDto.VerificationTokenMessageDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

public interface RegistrationService {
    @Transactional
    void resendVerificationEmail(UserEmailDto emailDto, HttpServletRequest request);

    void sendVerificationEmail(HttpServletRequest request, UserEmailDto activatedUserDto);

    @Transactional
    UserResponseDto completeRegistration(RegistrationDto registrationDto, HttpServletRequest request);

    @Transactional
    VerificationTokenMessageDto confirmUserRegistration(String token, Locale locale);
}
