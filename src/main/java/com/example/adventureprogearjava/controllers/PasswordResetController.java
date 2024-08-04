package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.resetPasswordDto.PasswordResetDTO;
import com.example.adventureprogearjava.dto.resetPasswordDto.PasswordResetRequestDTO;
import com.example.adventureprogearjava.services.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Password Reset Controller",
        description = "API operations for password reset")
public class PasswordResetController {
    PasswordResetService passwordResetService;

    @PostMapping("/request")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Successful operation.",
            content = @Content(schema = @Schema(implementation = PasswordResetRequestDTO.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Request password reset",
            description = "Request password reset"
    )
    public ResponseEntity<Void> requestPasswordReset(@RequestBody PasswordResetRequestDTO passwordResetRequestDTO, HttpServletRequest request) {
        String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        passwordResetService.requestPasswordReset(passwordResetRequestDTO, appUrl);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reset")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Successful operation.",
            content = @Content(schema = @Schema(implementation = PasswordResetDTO.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Reset password",
            description = "Reset password"
    )
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody PasswordResetDTO passwordResetDTO) {
        passwordResetService.resetPassword(passwordResetDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

