package com.example.adventureprogearjava.config;

import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.event.OnPasswordResetRequestEvent;
import com.example.adventureprogearjava.services.MailService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordResetListener implements ApplicationListener<OnPasswordResetRequestEvent> {
    MailService mailService;
    Environment environment;

    public PasswordResetListener(MailService mailService, Environment environment) {
        this.mailService = mailService;
        this.environment = environment;
    }

    @Override
    public void onApplicationEvent(OnPasswordResetRequestEvent event) {
        this.sendPasswordResetEmail(event);
    }

    private void sendPasswordResetEmail(OnPasswordResetRequestEvent event) {
        User user = event.getUser();
        String token = user.getPasswordResetToken();

        String subject = environment.getProperty("email.reset.subject", "Password Reset Request");

        String url = "https://incredible-creation-production.up.railway.app/reset-password?token=" + token;

        String message = environment.getProperty("email.reset.body", "To reset your password, click the following link:") + "\r\n" + url;

        mailService.sendEmail(user.getEmail(), subject, message);
    }

}
