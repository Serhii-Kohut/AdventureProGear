package com.example.adventureprogearjava.dto.registrationDto;

import com.example.adventureprogearjava.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDto {
    Long id;

    String name;

    String surname;

    String email;

    Role role;

    boolean verified;

    @JsonIgnore
    String password;

    public UserResponseDto(Long id, String name, String surname, String email, Role role, boolean verified) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.verified = verified;
    }
}
