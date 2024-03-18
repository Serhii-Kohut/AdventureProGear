package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.registrationDto.UserEmailDto;
import com.example.adventureprogearjava.dto.registrationDto.VerificationTokenDto;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.exceptions.NoUsersFoundException;
import com.example.adventureprogearjava.repositories.UserRepository;
import com.example.adventureprogearjava.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    @Value("${verification.token.expiration}")
    int expiryTimeInMinutes;

    final UserRepository userRepository;

    public VerificationTokenServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void createVerificationToken(UserEmailDto emailDto, String token) {
        String email = emailDto.getEmail().toLowerCase();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoUsersFoundException("User not found with email: " + email));
        user.updateVerificationToken(token, calculateExpiryDate());

        userRepository.save(user);

    }

    @Override
    public VerificationTokenDto getVerificationToken(String token) {
        return userRepository.findByVerificationToken(token)
                .filter(user -> !user.isVerificationTokenExpired())
                .map(user -> new VerificationTokenDto(user.getVerificationToken(), user.getTokenExpiryDate()))
                .orElse(null);
    }

    private LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plusMinutes(expiryTimeInMinutes);
    }
}
