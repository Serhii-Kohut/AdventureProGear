package com.example.adventureprogearjava.config;

import com.example.adventureprogearjava.dto.registrationDto.UserEmailDto;
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
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    MailService mailService;
    VerificationTokenService verificationTokenService;
    Environment environment;

    public RegistrationListener(MailService mailService, VerificationTokenService verificationTokenService, Environment environment) {
        this.mailService = mailService;
        this.verificationTokenService = verificationTokenService;
        this.environment = environment;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        UserEmailDto userEmailDto = event.getUserDto();
        String token = UUID.randomUUID().toString();

        verificationTokenService.createVerificationToken(userEmailDto, token);

        String recipientAddress = userEmailDto.getEmail();
        String subject = environment.getProperty("email.registration.subject", "Registration Confirmation");
        String confirmationUrl = "https://empowering-happiness-production.up.railway.app" + event.getAppUrl() + "/api/public/registration/confirmation?token=" + token;
        String emailBody = environment.getProperty("email.registration.body", "To complete the registration, please follow the link:") + "\r\n" + confirmationUrl;

        mailService.sendEmail(recipientAddress, subject, emailBody);
    }
}
