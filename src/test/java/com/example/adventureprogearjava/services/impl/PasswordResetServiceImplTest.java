package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.resetPasswordDto.PasswordResetDTO;
import com.example.adventureprogearjava.dto.resetPasswordDto.PasswordResetRequestDTO;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.event.OnPasswordResetRequestEvent;
import com.example.adventureprogearjava.exceptions.InvalidTokenException;
import com.example.adventureprogearjava.exceptions.NoUsersFoundException;
import com.example.adventureprogearjava.exceptions.PasswordMismatchException;
import com.example.adventureprogearjava.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordResetServiceImplTest {
    @Mock
    UserRepository userRepository;

    @Mock
    ApplicationEventPublisher applicationEventPublisher;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    PasswordResetServiceImpl passwordResetService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void requestPasswordReset_ShouldPublishEvent_WhenUserExists() {
        // Arrange
        String email = "user@example.com";
        String appUrl = "http://example.com";
        User user = new User();
        user.setEmail(email);

        PasswordResetRequestDTO passwordResetRequestDTO = new PasswordResetRequestDTO();
        passwordResetRequestDTO.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        ArgumentCaptor<OnPasswordResetRequestEvent> eventCaptor = ArgumentCaptor.forClass(OnPasswordResetRequestEvent.class);

        // Act
        passwordResetService.requestPasswordReset(passwordResetRequestDTO, appUrl);

        // Assert
        verify(userRepository).save(user);
        verify(applicationEventPublisher).publishEvent(eventCaptor.capture());

        OnPasswordResetRequestEvent event = eventCaptor.getValue();
        assertEquals(user, event.getUser());
        assertEquals(appUrl, event.getAppUrl());
    }

    @Test
    void requestPasswordReset_ShouldThrowException_WhenUserDoesNotExist() {
        // Arrange
        String email = "nonexistent@example.com";
        PasswordResetRequestDTO passwordResetRequestDTO = new PasswordResetRequestDTO();
        passwordResetRequestDTO.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        NoUsersFoundException exception = assertThrows(NoUsersFoundException.class,
                () -> passwordResetService.requestPasswordReset(passwordResetRequestDTO, "https://example.com"));

        assertEquals("No user with this email " + email, exception.getMessage());
    }

    @Test
    void resetPassword_ShouldResetPassword_WhenTokenIsValidAndPasswordsMatch() {
        // Arrange
        String token = UUID.randomUUID().toString();
        String newPassword = "newPassword1!";
        String encodedPassword = "encodedPassword";
        User user = new User();
        user.setPasswordResetToken(token);

        when(userRepository.findByPasswordResetToken(token)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);

        PasswordResetDTO passwordResetDTO = new PasswordResetDTO();
        passwordResetDTO.setToken(token);
        passwordResetDTO.setNewPassword(newPassword);
        passwordResetDTO.setConfirmPassword(newPassword);

        // Act
        passwordResetService.resetPassword(passwordResetDTO);

        // Assert
        verify(userRepository, times(2)).save(user);
        assertEquals(encodedPassword, user.getPassword());
        assertNull(user.getPasswordResetToken());
    }

    @Test
    void resetPassword_ShouldThrowException_WhenTokenIsInvalid() {
        // Arrange
        String token = UUID.randomUUID().toString();
        when(userRepository.findByPasswordResetToken(token)).thenReturn(Optional.empty());

        PasswordResetDTO passwordResetDTO = new PasswordResetDTO();
        passwordResetDTO.setToken(token);
        passwordResetDTO.setNewPassword("newPassword1!");
        passwordResetDTO.setConfirmPassword("newPassword1!");

        // Act & Assert
        InvalidTokenException exception = assertThrows(InvalidTokenException.class,
                () -> passwordResetService.resetPassword(passwordResetDTO));

        assertEquals("Invalid password reset token", exception.getMessage());
    }

    @Test
    void resetPassword_ShouldThrowException_WhenPasswordsDoNotMatch() {
        // Arrange
        String token = UUID.randomUUID().toString();
        User user = new User();
        user.setPasswordResetToken(token);

        when(userRepository.findByPasswordResetToken(token)).thenReturn(Optional.of(user));

        PasswordResetDTO passwordResetDTO = new PasswordResetDTO();
        passwordResetDTO.setToken(token);
        passwordResetDTO.setNewPassword("newPassword1!");
        passwordResetDTO.setConfirmPassword("differentPassword1!");

        // Act & Assert
        PasswordMismatchException exception = assertThrows(PasswordMismatchException.class,
                () -> passwordResetService.resetPassword(passwordResetDTO));

        assertEquals("New password and confirm password do not match", exception.getMessage());
    }


}
