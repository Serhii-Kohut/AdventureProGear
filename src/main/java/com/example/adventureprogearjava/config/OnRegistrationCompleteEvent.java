package com.example.adventureprogearjava.config;

import com.example.adventureprogearjava.dto.registrationDto.UserEmailDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    String appUrl;
    Locale locale;
    UserEmailDto userDto;

    public OnRegistrationCompleteEvent(
            UserEmailDto userDto, Locale locale, String appUrl) {
        super(userDto);
        this.userDto = userDto;
        this.locale = locale;
        this.appUrl = appUrl;
    }

}
