package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.dto.UserDTO;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.exceptions.NoContentException;
import com.example.adventureprogearjava.exceptions.NoUsersFoundException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.exceptions.UserAlreadyExistsException;
import com.example.adventureprogearjava.mapper.UserMapper;
import com.example.adventureprogearjava.repositories.UserRepository;
import com.example.adventureprogearjava.services.CRUDService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CRUDUserServiceImpl implements CRUDService<UserDTO> {

    UserRepository userRepository;
    UserMapper userMapper = UserMapper.MAPPER;

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
    public void update(UserDTO userDTO, Long id) {
        log.info("Updating user with id: {}", id);

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found with id: {}", id);
                    return new ResourceNotFoundException("User not found with id " + id);
                });

        User userToUpdate = userMapper.toEntity(userDTO);

        userToUpdate.setId(existingUser.getId());

        userToUpdate.setName(userDTO.getName());
        userToUpdate.setEmail(userDTO.getEmail());
        userToUpdate.setPhoneNumber(userDTO.getPhoneNumber());
        userToUpdate.setVerified(userDTO.isVerified());
        userToUpdate.setDate(userDTO.getDate());
        userToUpdate.setRole(userDTO.getRole());

        userRepository.save(userToUpdate);

    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user with id: {}", id);

        if (!userRepository.existsById(id)) {
            log.warn("No user present!");
            throw new NoContentException("No user present!");
        }

        userRepository.deleteById(id);
    }
}