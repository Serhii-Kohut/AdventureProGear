package com.example.adventureprogearjava.event;

import com.example.adventureprogearjava.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OnPasswordResetRequestEvent extends ApplicationEvent {
    User user;
    String appUrl;

    public OnPasswordResetRequestEvent(User user, String appUrl) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
    }
}
