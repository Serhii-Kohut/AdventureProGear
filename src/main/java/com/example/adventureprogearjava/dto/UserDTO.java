package com.example.adventureprogearjava.dto;

import com.example.adventureprogearjava.entity.enums.Role;
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

    @NotBlank(message = "Name is mandatory")
    String name;

    @NotBlank(message = "Surname is mandatory")
    String surname;

    @Email(message = "Email should be valid")
    String email;

    @Pattern(regexp="(^$|[0-9]{10})", message="Phone number should be valid")
    String phoneNumber;

    @NotNull(message = "Verified field is mandatory")
    boolean verified;

    @PastOrPresent(message = "Date should be in the past or present")
    LocalDate date;

    Role role;
}
