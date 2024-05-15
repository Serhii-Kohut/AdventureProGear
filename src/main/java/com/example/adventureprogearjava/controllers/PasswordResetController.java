package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.resetPasswordDto.PasswordResetDTO;
import com.example.adventureprogearjava.dto.resetPasswordDto.PasswordResetRequestDTO;
import com.example.adventureprogearjava.services.PasswordResetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/password-reset")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordResetController {
    PasswordResetService passwordResetService;

    @PostMapping("/request")
    public ResponseEntity<Void> requestPasswordReset(@RequestBody PasswordResetRequestDTO passwordResetRequestDTO, HttpServletRequest request) {
        String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        passwordResetService.requestPasswordReset(passwordResetRequestDTO, appUrl);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody PasswordResetDTO passwordResetDTO) {
        passwordResetService.resetPassword(passwordResetDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
