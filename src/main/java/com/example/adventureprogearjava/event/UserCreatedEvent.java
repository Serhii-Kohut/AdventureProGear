package com.example.adventureprogearjava.event;

import com.example.adventureprogearjava.dto.UserCreateDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserCreatedEvent extends ApplicationEvent {
    UserCreateDTO userCreateDTO;

    public UserCreatedEvent(Object source, UserCreateDTO userCreateDTO) {
        super(source);
        this.userCreateDTO = userCreateDTO;
    }
}
