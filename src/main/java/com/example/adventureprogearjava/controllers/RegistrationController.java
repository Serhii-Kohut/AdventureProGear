package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.registrationDto.RegistrationDto;
import com.example.adventureprogearjava.dto.registrationDto.UserEmailDto;
import com.example.adventureprogearjava.dto.registrationDto.UserResponseDto;
import com.example.adventureprogearjava.dto.registrationDto.VerificationTokenMessageDto;
import com.example.adventureprogearjava.services.impl.RegistrationServiceImpl;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;


@RestController
@RequestMapping("/api/public/registration")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Registration Controller",
        description = "API operations for user registration")
public class RegistrationController {
    RegistrationServiceImpl registrationService;

    @PostMapping("/register")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Successful operation.",
            content = @Content(schema = @Schema(implementation = RegistrationDto.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Registration  of new user",
            description = "Registration of new user"
    )
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationDto registrationDto,
                                          HttpServletRequest request) {
        try {
            UserResponseDto responseDto = registrationService.completeRegistration(registrationDto, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Successful operation.",
            content = @Content(schema = @Schema(implementation = UserEmailDto.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Resend verification email of new user",
            description = "Resend verification email of new user"
    )
    public ResponseEntity<?> resendVerificationEmail(@Valid @RequestBody UserEmailDto emailDto,
                                                     HttpServletRequest request) {
        try {
            registrationService.resendVerificationEmail(emailDto, request);
            return ResponseEntity.ok("Verification email sent successfully to: " + emailDto.getEmail());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("User with given email does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/confirmation")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Successful operation.",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Confirmation of user registration",
            description = "Confirmation of user registration"
    )
    public ResponseEntity<?> confirmation(@RequestParam("token") String token, WebRequest request) {
        VerificationTokenMessageDto result = registrationService.confirmUserRegistration(token, request.getLocale());

        return ResponseEntity.status(result.getStatus())
                .body(new ApiResponse(result.isSuccess(), result.getMessage()));
    }

    public record ApiResponse(boolean success, String message) {
    }

}
