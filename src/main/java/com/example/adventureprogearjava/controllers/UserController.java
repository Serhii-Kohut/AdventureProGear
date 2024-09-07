package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.annotation.userController.*;
import com.example.adventureprogearjava.dto.PasswordUpdateDTO;
import com.example.adventureprogearjava.dto.UserCreateDTO;
import com.example.adventureprogearjava.dto.UserDTO;
import com.example.adventureprogearjava.dto.UserUpdateDTO;
import com.example.adventureprogearjava.dto.registrationDto.UserEmailDto;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.services.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "User Controller",
        description = "API operations with users")
public class UserController {

    UserServiceImpl crudUserService;

    @GetAllUsers(path = "/all")
    public List<UserDTO> getAllUsers() {
        return crudUserService.getAll();
    }

    @GetMe(path = "/me")
    public UserDTO getUser(@AuthenticationPrincipal User user) {
        return crudUserService.getById(user.getId());
    }

    @GetUserById(path = "/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return crudUserService.getById(id);
    }

    @CreateUser(path = "")
    public UserCreateDTO createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        return crudUserService.create(userCreateDTO);
    }

    @UpdateMe(path = "/me/update")
    public UserDTO updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO, @AuthenticationPrincipal User user) {
        return crudUserService.update(userUpdateDTO, user.getId());
    }

    @UpdateMyEmail(path = "/me/update-email")
    public void updateEmail(@Valid @RequestBody UserEmailDto userEmailDto, @AuthenticationPrincipal User user, HttpServletRequest request) {
        crudUserService.updateEmail(userEmailDto, user.getId(), request);
    }

    @UpdatePassword(path = "/me/update-password")
    public void updatePassword(@Valid @RequestBody PasswordUpdateDTO passwordUpdateDTO, @AuthenticationPrincipal User user) {
        crudUserService.updatePassword(passwordUpdateDTO, user.getId());
    }

    @DeleteUser(path = "")
    public void deleteUser(@AuthenticationPrincipal User user) {
        crudUserService.delete(user.getId());
    }
}
