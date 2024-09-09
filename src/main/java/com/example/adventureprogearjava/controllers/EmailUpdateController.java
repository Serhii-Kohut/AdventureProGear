package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.annotation.emailUpdateController.EmailConfirmation;
import com.example.adventureprogearjava.dto.registrationDto.VerificationTokenMessageDto;
import com.example.adventureprogearjava.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/email/update")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailUpdateController {
    UserService userService;

    @EmailConfirmation(path = "/confirmation")
    public ResponseEntity<?> confirmNewEmail(@RequestParam("token") String token, HttpServletRequest request) {
        VerificationTokenMessageDto result = userService.confirmUpdateEmail(token, request.getLocale());
        return ResponseEntity.status(result.getStatus())
                .body(result);
    }
}
