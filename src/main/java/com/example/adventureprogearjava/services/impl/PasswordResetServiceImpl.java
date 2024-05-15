package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.resetPasswordDto.PasswordResetDTO;
import com.example.adventureprogearjava.dto.resetPasswordDto.PasswordResetRequestDTO;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.event.OnPasswordResetRequestEvent;
import com.example.adventureprogearjava.exceptions.InvalidTokenException;
import com.example.adventureprogearjava.exceptions.NoUsersFoundException;
import com.example.adventureprogearjava.exceptions.PasswordMismatchException;
import com.example.adventureprogearjava.repositories.UserRepository;
import com.example.adventureprogearjava.services.PasswordResetService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordResetServiceImpl implements PasswordResetService {
    UserRepository userRepository;
    ApplicationEventPublisher applicationEventPublisher;
    PasswordEncoder passwordEncoder;

    @Override
    public void requestPasswordReset(PasswordResetRequestDTO passwordResetRequestDTO, String appUrl) {
        User user = userRepository.findByEmail(passwordResetRequestDTO.getEmail())
                .orElseThrow(() -> new NoUsersFoundException("No user with this email " + passwordResetRequestDTO.getEmail()));

        String token = UUID.randomUUID().toString();

        user.setPasswordResetToken(token);
        userRepository.save(user);

        OnPasswordResetRequestEvent event = new OnPasswordResetRequestEvent(user, appUrl);
        applicationEventPublisher.publishEvent(event);

    }

    @Override
    public void resetPassword(PasswordResetDTO passwordResetDTO) {
        User user = userRepository.findByPasswordResetToken(passwordResetDTO.getToken())
                .orElseThrow(() -> new InvalidTokenException("Invalid password reset token"));

        if (!passwordResetDTO.getNewPassword().equals(passwordResetDTO.getConfirmPassword())) {
            throw new PasswordMismatchException("New password and confirm password do not match");
        }

        user.setPassword(passwordEncoder.encode(passwordResetDTO.getNewPassword()));
        userRepository.save(user);

        user.setPasswordResetToken(null);
        userRepository.save(user);
    }
}
