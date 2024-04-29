package com.example.adventureprogearjava.event;

import com.example.adventureprogearjava.dto.registrationDto.UserEmailDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OnEmailUpdateEvent extends ApplicationEvent {
    String appUrl;
    UserEmailDto userEmailDto;

    public OnEmailUpdateEvent(Object source, String appUrl, UserEmailDto userEmailDto) {
        super(source);
        this.appUrl = appUrl;
        this.userEmailDto = userEmailDto;
    }
}
