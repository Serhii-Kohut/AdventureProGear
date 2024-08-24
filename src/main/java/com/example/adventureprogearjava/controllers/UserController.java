package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.PasswordUpdateDTO;
import com.example.adventureprogearjava.dto.UserCreateDTO;
import com.example.adventureprogearjava.dto.UserDTO;
import com.example.adventureprogearjava.dto.UserUpdateDTO;
import com.example.adventureprogearjava.dto.registrationDto.UserEmailDto;
import com.example.adventureprogearjava.entity.User;
import com.example.adventureprogearjava.services.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(
            summary = "Get all users. Available for ADMIN.",
            description = "Retrieves all available users."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = UserDTO.class))
    )
    public List<UserDTO> getAllUsers() {
        return crudUserService.getAll();
    }

    @GetMapping("/me")
    @Operation(
            summary = "Get info about me",
            description = "Retrieves info about me"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = UserDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public UserDTO getUser(@Parameter(
            description = "Info about me",
            required = true
    ) @AuthenticationPrincipal User user) {
        return crudUserService.getById(user.getId());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(
            summary = "Get user by id. Available for ADMIN.",
            description = "Retrieves User by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = UserDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public UserDTO getUserById(@Parameter(
            description = "ID of the user",
            required = true
    ) @PathVariable Long id) {
        return crudUserService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(
            responseCode = "201",
            description = "Successful operation.",
            content = @Content(schema = @Schema(implementation = UserDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Creation of new user.Available for ADMIN.",
            description = "Creation of new user"
    )
    public UserCreateDTO createUser(@Valid
                                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                            description = "User data, required for creation",
                                            required = true,
                                            content = @Content(schema = @Schema(implementation = UserCreateDTO.class))
                                    ) @RequestBody UserCreateDTO userCreateDTO) {
        return crudUserService.create(userCreateDTO);
    }

    @PutMapping("/me/update")
    @Operation(
            summary = "Update my info: name, surname, phoneNumber, streetAndHouseNumber, city, postalCode.",
            description = "Updates my details."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = UserUpdateDTO.class))
    )
    public UserDTO updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO, @AuthenticationPrincipal User user) {
        return crudUserService.update(userUpdateDTO, user.getId());
    }

    @PutMapping("/me/update-email")
    @Operation(
            summary = "Update my email",
            description = "Updates the email of an existing user and sends a confirmation email"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = UserEmailDto.class))
    )
    public void updateEmail(@Valid @RequestBody UserEmailDto userEmailDto, @AuthenticationPrincipal User user, HttpServletRequest request) {
        crudUserService.updateEmail(userEmailDto, user.getId(), request);
    }


    @PutMapping("/me/update-password")
    @Operation(
            summary = "Update my password",
            description = "Updates the password of an existing user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(schema = @Schema(implementation = PasswordUpdateDTO.class))
    )
    public void updatePassword(@Valid @RequestBody PasswordUpdateDTO passwordUpdateDTO, @AuthenticationPrincipal User user) {
        crudUserService.updatePassword(passwordUpdateDTO, user.getId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation."
    )
    @ApiResponse(
            responseCode = "204",
            description = "No content present.",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Delete my own account.",
            description = "Deleting of user."
    )
    public void deleteUser(@Parameter(
            description = "ID of the user",
            required = true
    ) @AuthenticationPrincipal User user) {
        crudUserService.delete(user.getId());
    }

}
