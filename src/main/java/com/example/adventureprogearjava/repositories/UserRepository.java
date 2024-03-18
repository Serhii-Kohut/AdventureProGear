package com.example.adventureprogearjava.repositories;

import com.example.adventureprogearjava.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByVerificationToken(String verificationToken);
}
