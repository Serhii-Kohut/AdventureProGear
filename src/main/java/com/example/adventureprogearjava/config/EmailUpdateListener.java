package com.example.adventureprogearjava.config;

import com.example.adventureprogearjava.dto.registrationDto.UserEmailDto;
import com.example.adventureprogearjava.event.OnEmailUpdateEvent;
import com.example.adventureprogearjava.services.MailService;
import com.example.adventureprogearjava.services.VerificationTokenService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailUpdateListener implements ApplicationListener<OnEmailUpdateEvent> {
    MailService mailService;
    VerificationTokenService verificationTokenService;
    Environment environment;

    public EmailUpdateListener(MailService mailService, VerificationTokenService verificationTokenService, Environment environment) {
        this.mailService = mailService;
        this.verificationTokenService = verificationTokenService;
        this.environment = environment;
    }

    @Override
    public void onApplicationEvent(OnEmailUpdateEvent event) {
        this.confirmEmailUpdate(event);
    }

    private void confirmEmailUpdate(OnEmailUpdateEvent event) {
        UserEmailDto userEmailDto = event.getUserEmailDto();
        String token = UUID.randomUUID().toString();

        verificationTokenService.createVerificationToken(userEmailDto, token);

        String recipientAddress = userEmailDto.getEmail();
        String subject = environment.getProperty("email.update.subject", "Email Update Confirmation");
        String confirmationUrl = "https://empowering-happiness-production.up.railway.app" + event.getAppUrl() + "/api/public/email/update/confirmation?token=" + token;
        String emailBody = environment.getProperty("email.update.body", "To confirm the email update, please follow the link:") + "\r\n" + confirmationUrl;

        mailService.sendEmail(recipientAddress, subject, emailBody);


    }
}
