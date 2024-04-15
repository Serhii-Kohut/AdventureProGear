package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.authToken.AuthenticationRequestDto;
import com.example.adventureprogearjava.dto.authToken.AuthenticationResponseDto;
import com.example.adventureprogearjava.dto.authToken.RefreshTokenRequestDto;
import com.example.adventureprogearjava.dto.authToken.RefreshTokenResponseDto;
import com.example.adventureprogearjava.dto.registrationDto.UserResponseDto;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.exceptions.UserIsNotActiveException;
import com.example.adventureprogearjava.mapper.UserMapper;
import io.jsonwebtoken.JwtException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    JwtService jwtService;
    PasswordEncoder passwordEncoder;
    UserService userService;
    UserMapper userMapper = UserMapper.MAPPER;

    public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        String authHeader = refreshTokenRequestDto.getRefreshToken();

        RefreshTokenResponseDto refreshTokenResponseDto = new RefreshTokenResponseDto();

        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new BadCredentialsException("Missing refresh token");
        }

        String refreshToken = authHeader.substring(7);

        if (jwtService.extractType(refreshToken).equals("accessToken")) {
            throw new JwtException("Access token cannot be used for refreshing token");
        }

        UserResponseDto userResponseDto = userService.getUserByEmail(jwtService.extractEmail(refreshToken));
        User user = userMapper.userResponseDtoToUser(userResponseDto);

        if (user.getVerified().equals(true)) {
            refreshTokenResponseDto.setAccessToken(jwtService.generateAccessToken(user));
            refreshTokenResponseDto.setRefreshToken(jwtService.generateRefreshToken(user));

            return refreshTokenResponseDto;
        } else {
            throw new UserIsNotActiveException("User account is not valid");
        }
    }

    public AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto) {
        UserResponseDto userResponseDto = userService.getUserByEmail(authenticationRequestDto.getEmail());

        User user = userMapper.userResponseDtoToUser(userResponseDto);

        if (user.getVerified().equals(true)) {
            AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();

            if (passwordEncoder.matches(authenticationRequestDto.getPassword(), user.getPassword())) {
                authenticationResponseDto.setAccessToken(jwtService.generateAccessToken(user));
                authenticationResponseDto.setRefreshToken(jwtService.generateRefreshToken(user));

                return authenticationResponseDto;
            } else {
                throw new BadCredentialsException("Wrong password");
            }
        } else {
            throw new UserIsNotActiveException("User account is not valid");
        }
    }
}
