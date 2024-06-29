package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.authToken.AuthenticationRequestDto;
import com.example.adventureprogearjava.dto.authToken.AuthenticationResponseDto;
import com.example.adventureprogearjava.dto.authToken.RefreshTokenRequestDto;
import com.example.adventureprogearjava.dto.authToken.RefreshTokenResponseDto;
import com.example.adventureprogearjava.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Authentication Controller",
        description = "API operations for user authentication")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Successful operation.",
            content = @Content(schema = @Schema(implementation = AuthenticationResponseDto.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Login of a user",
            description = "Login of a user"
    )
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody
                                                           AuthenticationRequestDto authenticationRequestDto) {
        AuthenticationResponseDto authenticationResponseDto = authenticationService.login(authenticationRequestDto);

        return ResponseEntity.ok()
                .body(authenticationResponseDto);
    }

    @PostMapping("/refresh-token")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Successful operation.",
            content = @Content(schema = @Schema(implementation = RefreshTokenResponseDto.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Refresh token of a user",
            description = "Refresh token of a user"
    )
    public ResponseEntity<RefreshTokenResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        RefreshTokenResponseDto refreshTokenResponseDto = authenticationService.refreshToken(refreshTokenRequestDto);
        return ResponseEntity.ok().body(refreshTokenResponseDto);
    }
}
