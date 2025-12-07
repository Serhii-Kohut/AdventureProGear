package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.services.MailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    //    JavaMailSender javaMailSender;
//
//    public MailServiceImpl(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
//
//    @Override
//    public void sendEmail(String to, String subject, String body) {
//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setTo(to);
//        email.setSubject(subject);
//        email.setText(body);
//        javaMailSender.send(email);
//    }

    RestTemplate restTemplate;  // Інжектується автоматично або через @Bean

    @Value("${resend.api-key}")
    @NonFinal
    String apiKey;

    @Value("${resend.from}")
    @NonFinal
    String from;

    @Override
    public void sendEmail(String to, String subject, String body) {
        String url = "https://api.resend.com/emails";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("from", from);
        requestBody.put("to", to);
        requestBody.put("subject", subject);
        requestBody.put("text", body);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to send email via Resend: " + response.getBody());
        }
    }

}
