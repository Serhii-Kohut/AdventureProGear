package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.authToken.AuthenticationRequestDto;
import com.example.adventureprogearjava.dto.authToken.AuthenticationResponseDto;
import com.example.adventureprogearjava.dto.authToken.RefreshTokenRequestDto;
import com.example.adventureprogearjava.dto.authToken.RefreshTokenResponseDto;
import com.example.adventureprogearjava.services.AuthenticationService;
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
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody
                                                           AuthenticationRequestDto authenticationRequestDto) {
        AuthenticationResponseDto authenticationResponseDto = authenticationService.login(authenticationRequestDto);

        return ResponseEntity.ok()
                .body(authenticationResponseDto);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponseDto> refreshToken(@RequestBody HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        RefreshTokenRequestDto refreshTokenRequestDto = new RefreshTokenRequestDto(authHeader);

        RefreshTokenResponseDto refreshTokenResponseDto = authenticationService.refreshToken(refreshTokenRequestDto);

        return ResponseEntity.ok()
                .body(refreshTokenResponseDto);
    }

}
