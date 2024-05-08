package com.example.adventureprogearjava.entity;

import com.example.adventureprogearjava.entity.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity implements UserDetails {

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "surname", nullable = false)
    String surname;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "phone_number", unique = true)
    String phoneNumber;

    @Column(name = "verified", nullable = false)
    Boolean verified;

    @Column(name = "registration_date")
    LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role;

    @Column(name = "street_and_house_number")
    String streetAndHouseNumber;

    @Column(name = "city")
    String city;

    @Column(name = "postal_code")
    String postalCode;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    List<Order> orders;

    @Column(name = "verification_token", unique = true)
    String verificationToken;

    @Column(name = "token_expiry_date")
    LocalDateTime tokenExpiryDate;

    public void updateVerificationToken(String newToken, LocalDateTime newExpiryDate) {
        this.verificationToken = newToken;
        this.tokenExpiryDate = newExpiryDate;
    }

    public boolean isVerificationTokenExpired() {
        return tokenExpiryDate.isBefore(LocalDateTime.now());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
