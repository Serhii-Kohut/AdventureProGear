package com.example.adventureprogearjava.dto.registrationDto;

import com.example.adventureprogearjava.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDto {
    Long id;

    @NotBlank(message = "Name is mandatory")
    String name;

    @NotBlank(message = "Surname is mandatory")
    String surname;

    @Email(message = "Email should be valid")
    String email;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password should be valid")
    String password;

    @NotNull(message = "Role cannot be null")
    Role role;
}
