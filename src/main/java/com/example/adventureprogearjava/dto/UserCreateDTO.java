package com.example.adventureprogearjava.dto;

import com.example.adventureprogearjava.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateDTO {

    public UserCreateDTO() {
        this.verified = false;
        this.date = LocalDate.now();
        this.role = Role.USER;
    }

    @NotBlank(message = "Name is mandatory")
    String name;

    @NotBlank(message = "Surname is mandatory")
    String surname;

    @Email(message = "Email should be valid")
    String email;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password should be valid")
    String password;

    @NotNull(message = "Verified field is mandatory")
    boolean verified;

    @PastOrPresent(message = "Date should be in the past or present")
    LocalDate date;

    Role role;


}
