package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.registrationDto.UserEmailDto;
import com.example.adventureprogearjava.dto.registrationDto.VerificationTokenDto;
import org.springframework.transaction.annotation.Transactional;

public interface VerificationTokenService {
    @Transactional
    void createVerificationToken(UserEmailDto email, String token);

    VerificationTokenDto getVerificationToken(String VerificationToken);
}
