package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.PasswordUpdateDTO;
import com.example.adventureprogearjava.dto.UserDTO;
import com.example.adventureprogearjava.dto.UserUpdateDTO;
import com.example.adventureprogearjava.dto.registrationDto.UserRequestDto;
import com.example.adventureprogearjava.dto.registrationDto.UserResponseDto;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.exceptions.NoUsersFoundException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.exceptions.UserAlreadyExistsException;
import com.example.adventureprogearjava.mapper.UserMapper;
import com.example.adventureprogearjava.repositories.UserRepository;
import com.example.adventureprogearjava.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper = UserMapper.MAPPER;
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAll() {
        log.info("Getting all users");

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new NoUsersFoundException("No users found in the database");
        }

        return users.stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NoUsersFoundException("User not found with email: " + email);
        }
        return userMapper.userToUserResponseDto(user.get());
    }

    @Override
    public UserDTO getById(Long id) {

        log.info("Getting user by id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found with id: {}", id);
                    return new ResourceNotFoundException("User not found with id " + id);
                });

        return userMapper.toDTO(user);
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) {
        log.info("Creating new user.");

        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }

        if (userDTO.getEmail() != null && userRepository.existsByEmail(userDTO.getEmail())) {
            log.warn("User with email {} already exists", userDTO.getEmail());

            throw new UserAlreadyExistsException("User with email " + userDTO.getEmail() + " already exists");
        }

        if (userDTO.getPhoneNumber() != null && userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            log.warn("User with phone number {} already exists", userDTO.getPhoneNumber());

            throw new UserAlreadyExistsException("User with phone number " + userDTO.getPhoneNumber() + " already exists");
        }

        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);

        return userMapper.toDTO(savedUser);
    }

    @Override
    @Transactional
    public UserResponseDto saveRegisteredUser(UserRequestDto registrationDto) {
        String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());

        User newUser = User
                .builder()
                .name(registrationDto.getName())
                .surname(registrationDto.getSurname())
                .email(registrationDto.getEmail().toLowerCase())
                .password(encodedPassword)
                .date(LocalDate.now())
                .role(registrationDto.getRole())
                .verified(false)
                .build();

        return userMapper.userToUserResponseDto(userRepository.save(newUser));
    }

    @Override
    @Transactional
    public void update(UserUpdateDTO userUpdateDTO, Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        if (userUpdateDTO.getName() != null) {
            existingUser.setName(userUpdateDTO.getName());
        }
        if (userUpdateDTO.getSurname() != null) {
            existingUser.setSurname(userUpdateDTO.getSurname());
        }
        if (userUpdateDTO.getEmail() != null) {
            existingUser.setEmail(userUpdateDTO.getEmail());
        }
        if (userUpdateDTO.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        }

        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void updatePassword(PasswordUpdateDTO passwordUpdateDTO, Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        if (passwordUpdateDTO.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(passwordUpdateDTO.getPassword()));
        }

        userRepository.save(existingUser);
    }


    @Override
    public void delete(Long id) {
        log.info("Deleting user with id: {}", id);

        if (!userRepository.existsById(id)) {
            log.warn("User not found with id: {}", id);
            throw new ResourceNotFoundException("User not found with id " + id);
        }

        userRepository.deleteById(id);
    }
}