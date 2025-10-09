package com.example.adventureprogearjava.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailConfig {
//    @Value("${spring.mail.username}")
//    String username;
//
//    @Value("${spring.mail.password}")
//    String password;
//
//    @Bean
//    @Primary
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//        mailSender.setUsername(this.username);
//        mailSender.setPassword(this.password);
//
//        Properties props = mailSender.getJavaMailProperties();
//
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
