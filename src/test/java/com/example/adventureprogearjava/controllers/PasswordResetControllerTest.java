package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.config.JwtProperties;
import com.example.adventureprogearjava.dto.resetPasswordDto.PasswordResetDTO;
import com.example.adventureprogearjava.services.PasswordResetService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PasswordResetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JwtProperties jwtProperties;

    @MockBean
    private PasswordResetService passwordResetService;

    @BeforeEach
    void setUp() {
        Mockito.reset(passwordResetService);
    }

    private String createMockJWT(String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("type", "test");
        claims.put("id", 1);

        return Jwts.builder()
                .setSubject("mockUser")
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration()))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecretKey())))
                .compact();
    }

    @Test
    @WithMockUser // Автентифікація тестовим користувачем
    public void requestPasswordReset_ShouldReturnOk_WhenRequestIsValid() throws Exception {
        // Створення JWT токену
        String jwt = createMockJWT("ADMIN");

        // Відправка POST запиту з аутентифікаційним токеном
        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/password-reset/request")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"user@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    public void resetPassword_ShouldReturnOk_WhenRequestIsValid() throws Exception {
        String validToken = "validToken";
        String newPassword = "Abc@9876";
        String confirmPassword = "Abc@9876";

        // Ваша імітована реалізація створення JWT
        String jwt = createMockJWT("ADMIN");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/password-reset/reset")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\":\"" + validToken + "\",\"newPassword\":\"" + newPassword + "\",\"confirmPassword\":\"" + confirmPassword + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void resetPassword_ShouldReturnBadRequest_WhenPasswordsDoNotMatch() throws Exception {
        PasswordResetDTO passwordResetDTO = new PasswordResetDTO();
        passwordResetDTO.setToken("validToken");
        passwordResetDTO.setNewPassword("Abc@9876");
        passwordResetDTO.setConfirmPassword("Abc@9876111");

        String jwt = createMockJWT("ADMIN");

        mockMvc.perform(post("/api/public/password-reset/reset")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\":\"validToken\",\"newPassword\":\"NewPassword1!\",\"confirmPassword\":\"DifferentPassword1!\"}"))
                .andExpect(status().isBadRequest());
    }

}
