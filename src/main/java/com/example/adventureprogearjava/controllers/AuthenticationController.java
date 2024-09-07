package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.annotation.authenticationController.LoginUser;
import com.example.adventureprogearjava.annotation.authenticationController.RefreshToken;
import com.example.adventureprogearjava.dto.authToken.AuthenticationRequestDto;
import com.example.adventureprogearjava.dto.authToken.AuthenticationResponseDto;
import com.example.adventureprogearjava.dto.authToken.RefreshTokenRequestDto;
import com.example.adventureprogearjava.dto.authToken.RefreshTokenResponseDto;
import com.example.adventureprogearjava.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

    @LoginUser(path = "/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody
                                                           AuthenticationRequestDto authenticationRequestDto) {
        AuthenticationResponseDto authenticationResponseDto = authenticationService.login(authenticationRequestDto);

        return ResponseEntity.ok()
                .body(authenticationResponseDto);
    }

    @RefreshToken(path = "/refresh_token")
    public ResponseEntity<RefreshTokenResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        RefreshTokenResponseDto refreshTokenResponseDto = authenticationService.refreshToken(refreshTokenRequestDto);
        return ResponseEntity.ok().body(refreshTokenResponseDto);
    }
}
