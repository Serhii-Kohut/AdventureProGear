package com.example.adventureprogearjava.dto.resetPasswordDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordResetRequestDTO {
    String email;
}
