package com.example.adventureprogearjava.dto;

import com.example.adventureprogearjava.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {

    public UserDTO(String name, String surname, String email, String password, String phoneNumber, boolean verified, LocalDate date, Role role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.verified = verified;
        this.date = date;
        this.role = role;
    }

    @NotBlank(message = "Name is mandatory")
    String name;

    @NotBlank(message = "Surname is mandatory")
    String surname;

    @Email(message = "Email should be valid")
    String email;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password should be valid")
    @JsonIgnore
    String password;

    @Pattern(regexp = "(^$|\\+380[0-9]{9})", message = "Phone number should be valid")
    String phoneNumber;

    @NotNull(message = "Verified field is mandatory")
    boolean verified;

    @PastOrPresent(message = "Date should be in the past or present")
    LocalDate date;

    Role role;
}
