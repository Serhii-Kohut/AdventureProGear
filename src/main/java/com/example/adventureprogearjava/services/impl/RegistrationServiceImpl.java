package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.config.AuthMessages;
import com.example.adventureprogearjava.config.OnRegistrationCompleteEvent;
import com.example.adventureprogearjava.dto.registrationDto.RegistrationDto;
import com.example.adventureprogearjava.dto.registrationDto.UserEmailDto;
import com.example.adventureprogearjava.dto.registrationDto.UserRequestDto;
import com.example.adventureprogearjava.dto.registrationDto.UserResponseDto;
import com.example.adventureprogearjava.dto.registrationDto.VerificationTokenDto;
import com.example.adventureprogearjava.dto.registrationDto.VerificationTokenMessageDto;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.entity.enums.Role;
import com.example.adventureprogearjava.repositories.UserRepository;
import com.example.adventureprogearjava.services.RegistrationService;
import com.example.adventureprogearjava.services.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationServiceImpl implements RegistrationService {
    UserServiceImpl crudUserService;
    ApplicationEventPublisher eventPublisher;
    UserRepository userRepository;
    MessageSource messages;
    VerificationTokenService verificationTokenService;
    AuthMessages authMessages;

    public RegistrationServiceImpl(UserServiceImpl crudUserService, ApplicationEventPublisher eventPublisher, UserRepository userRepository, MessageSource messageSource, VerificationTokenService verificationTokenService, AuthMessages authMessages) {
        this.crudUserService = crudUserService;
        this.eventPublisher = eventPublisher;
        this.userRepository = userRepository;
        this.messages = messageSource;
        this.verificationTokenService = verificationTokenService;
        this.authMessages = authMessages;
    }

    @Override
    public UserResponseDto registerUser(RegistrationDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new IllegalStateException("Email " + registrationDto.getEmail() + " is already in use.");
        }

        UserRequestDto newUserDto = new UserRequestDto(
                null,
                registrationDto.getName(),
                registrationDto.getSurname(),
                registrationDto.getEmail(),
                registrationDto.getPassword(),
                Role.USER
        );

        return crudUserService.saveRegisteredUser(newUserDto);
    }

    @Override
    public void resendVerificationEmail(UserEmailDto emailDto, HttpServletRequest request) {
        crudUserService.getUserByEmail(emailDto.getEmail());
        sendVerificationEmail(request, emailDto);
    }

    @Override
    public void sendVerificationEmail(HttpServletRequest request, UserEmailDto activatedUserDto) {
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(activatedUserDto,
                request.getLocale(), request.getContextPath()));
    }

    @Override
    @Transactional
    public UserResponseDto completeRegistration(RegistrationDto registrationDto, HttpServletRequest request) {
        UserResponseDto newUserResponseDto = registerUser(registrationDto);

        sendVerificationEmail(request, new UserEmailDto(newUserResponseDto.getEmail()));

        return newUserResponseDto;
    }


    @Override
    @Transactional
    public VerificationTokenMessageDto confirmUserRegistration(String token, Locale locale) {
        VerificationTokenDto verificationTokenDto = verificationTokenService.getVerificationToken(token);
        if (Objects.isNull(verificationTokenDto)) {
            return new VerificationTokenMessageDto(false, authMessages.getInvalidToken(), HttpStatus.BAD_REQUEST);
        }

        Optional<User> user = userRepository.findByVerificationToken(token);
        if (user.isEmpty()) {
            String message = messages.getMessage("auth.message.tokenNotFound", null, locale);

            return new VerificationTokenMessageDto(false, message, HttpStatus.BAD_REQUEST);
        }

        if (verificationTokenDto.isExpired()) {
            return new VerificationTokenMessageDto(Boolean.FALSE, authMessages.getExpired(), HttpStatus.BAD_REQUEST);
        }

        user.get().setVerified(true);
        userRepository.save(user.get());

        return new VerificationTokenMessageDto(true, authMessages.getConfirmed(), HttpStatus.OK);

    }

}
