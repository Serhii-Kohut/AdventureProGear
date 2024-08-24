package com.example.adventureprogearjava.services.impl;

import com.example.adventureprogearjava.config.AuthMessages;
import com.example.adventureprogearjava.dto.PasswordUpdateDTO;
import com.example.adventureprogearjava.dto.UserCreateDTO;
import com.example.adventureprogearjava.dto.UserDTO;
import com.example.adventureprogearjava.dto.UserUpdateDTO;
import com.example.adventureprogearjava.dto.registrationDto.UserEmailDto;
import com.example.adventureprogearjava.dto.registrationDto.UserRequestDto;
import com.example.adventureprogearjava.dto.registrationDto.UserResponseDto;
import com.example.adventureprogearjava.dto.registrationDto.VerificationTokenDto;
import com.example.adventureprogearjava.dto.registrationDto.VerificationTokenMessageDto;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.event.OnEmailUpdateEvent;
import com.example.adventureprogearjava.event.UserCreatedEvent;
import com.example.adventureprogearjava.exceptions.NoUsersFoundException;
import com.example.adventureprogearjava.exceptions.ResourceNotFoundException;
import com.example.adventureprogearjava.exceptions.UserAlreadyExistsException;
import com.example.adventureprogearjava.mapper.UserMapper;
import com.example.adventureprogearjava.repositories.UserRepository;
import com.example.adventureprogearjava.services.UserService;
import com.example.adventureprogearjava.services.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper = UserMapper.MAPPER;
    PasswordEncoder passwordEncoder;
    ApplicationEventPublisher applicationEventPublisher;
    VerificationTokenService verificationTokenService;
    AuthMessages authMessages;
    MessageSource messages;

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
            throw new NoUsersFoundException("The email address '" + email + "' is not registered. Please register");
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
    public UserCreateDTO create(UserCreateDTO userCreateDTO) {
        log.info("Creating new user.");

        if (userCreateDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }

        if (userCreateDTO.getEmail() != null && userRepository.existsByEmail(userCreateDTO.getEmail())) {
            log.warn("User with email {} already exists", userCreateDTO.getEmail());

            throw new UserAlreadyExistsException("User with email " + userCreateDTO.getEmail() + " already exists");
        }

/*        if (userCreateDTO.getPhoneNumber() != null && userRepository.existsByPhoneNumber(userCreateDTO.getPhoneNumber())) {
            log.warn("User with phone number {} already exists", userCreateDTO.getPhoneNumber());

            throw new UserAlreadyExistsException("User with phone number " + userCreateDTO.getPhoneNumber() + " already exists");
        }*/

        User user = userMapper.toEntityFromCreateDto(userCreateDTO);
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        User savedUser = userRepository.save(user);

        applicationEventPublisher.publishEvent(new UserCreatedEvent(this, userMapper.toDTOForCreate(savedUser)));

        return userMapper.toDTOForCreate(savedUser);
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
    public UserDTO update(UserUpdateDTO userUpdateDTO, Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        if (userUpdateDTO.getName() != null) {
            existingUser.setName(userUpdateDTO.getName());
        }
        if (userUpdateDTO.getSurname() != null) {
            existingUser.setSurname(userUpdateDTO.getSurname());
        }

        if (userUpdateDTO.getPhoneNumber() != null) {
            Optional<User> userWithPhone = userRepository.findByPhoneNumber(userUpdateDTO.getPhoneNumber());
            if (userWithPhone.isPresent() && !userWithPhone.get().getId().equals(id)) {
                throw new IllegalArgumentException("Phone number is already in use");
            }
            existingUser.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        }

        if (userUpdateDTO.getStreetAndHouseNumber() != null) {
            existingUser.setStreetAndHouseNumber(userUpdateDTO.getStreetAndHouseNumber());
        }
        if (userUpdateDTO.getCity() != null) {
            existingUser.setCity(userUpdateDTO.getCity());
        }
        if (userUpdateDTO.getPostalCode() != null) {
            existingUser.setPostalCode(userUpdateDTO.getPostalCode());
        }

        userRepository.save(existingUser);
        
        return userMapper.toDTO(existingUser);
    }


    @Override
    @Transactional
    public void updateEmail(UserEmailDto userEmailDto, Long id, HttpServletRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NoUsersFoundException("User not found with id " + id));

        if (!passwordEncoder.matches(userEmailDto.getPassword(), existingUser.getPassword())) {
            throw new IllegalArgumentException("Entered password does not match the current password");
        }

        if (!userEmailDto.getPassword().equals(userEmailDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Password do not match");
        }

        if (userEmailDto.getEmail() != null && !userEmailDto.getEmail().equals(existingUser.getEmail())) {
            Optional<User> userWithEmail = userRepository.findByEmail(userEmailDto.getEmail());

            if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(id)) {
                throw new IllegalArgumentException("Email is already in use");
            } else if (userWithEmail.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already in use");
            }

            existingUser.setEmail(userEmailDto.getEmail());
            existingUser.setVerified(false);
            sendEmailUpdateConfirmation(request, new UserEmailDto(existingUser.getEmail(), null, null));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is not changed");
        }

        existingUser.setPassword(passwordEncoder.encode(userEmailDto.getPassword()));
        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void updatePassword(PasswordUpdateDTO passwordUpdateDTO, Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NoUsersFoundException("User not found with id " + id));

        if (passwordUpdateDTO.getPassword() != null) {
            if (!passwordUpdateDTO.getPassword().equals(passwordUpdateDTO.getConfirmPassword())) {
                throw new IllegalArgumentException("Passwords do not match");
            }
            existingUser.setPassword(passwordEncoder.encode(passwordUpdateDTO.getPassword()));
        }
        userRepository.save(existingUser);
    }


    @Override
    public void delete(Long id) {
        log.info("Deleting user with id: {}", id);

        if (!userRepository.existsById(id)) {
            log.warn("User not found with id: {}", id);
            throw new NoUsersFoundException("User not found with id " + id);
        }

        userRepository.deleteById(id);
    }

    public void sendEmailUpdateConfirmation(HttpServletRequest request, UserEmailDto updatedUserDto) {
        applicationEventPublisher.publishEvent(new OnEmailUpdateEvent(this, getAppUrl(request), updatedUserDto));
    }


    private String getAppUrl(HttpServletRequest request) {
        return request.getContextPath();
    }

    @Override
    @Transactional
    public VerificationTokenMessageDto confirmUpdateEmail(String token, Locale locale) {
        VerificationTokenDto verificationTokenDto = verificationTokenService.getVerificationToken(token);
        if (Objects.isNull(verificationTokenDto)) {
            return new VerificationTokenMessageDto(false, authMessages.getInvalidToken(), HttpStatus.BAD_REQUEST);
        }

        Optional<User> user = userRepository.findByVerificationToken(token);
        if (user.isEmpty()) {
            String message = messages.getMessage("auth.message.tokenNotFound", null, locale);

            return new VerificationTokenMessageDto(false, message, HttpStatus.BAD_REQUEST);
        }

        if (verificationTokenDto.isExpired()) {
            return new VerificationTokenMessageDto(Boolean.FALSE, authMessages.getExpired(), HttpStatus.BAD_REQUEST);
        }

        user.get().setVerified(true);
        userRepository.save(user.get());

        return new VerificationTokenMessageDto(true, authMessages.getConfirmed(), HttpStatus.OK);

    }
}