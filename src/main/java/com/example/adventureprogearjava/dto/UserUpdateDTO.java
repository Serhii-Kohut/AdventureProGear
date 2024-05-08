package com.example.adventureprogearjava.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateDTO {
    String name;
    String surname;

    @Email(message = "Email should be valid")
    String email;

    @Pattern(regexp = "(^$|\\+380[0-9]{9})", message = "Phone number should be valid")
    String phoneNumber;

    String streetAndHouseNumber;
    String city;
    String postalCode;
}
