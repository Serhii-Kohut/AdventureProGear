package com.example.adventureprogearjava.dto;

import com.example.adventureprogearjava.entity.enums.Role;
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
    String name;
    String surname;
    String email;
    String phoneNumber;
    boolean verified;
    LocalDate date;
    Role role;
}
