package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.PasswordUpdateDTO;
import com.example.adventureprogearjava.dto.UserDTO;
import com.example.adventureprogearjava.dto.UserUpdateDTO;
import com.example.adventureprogearjava.services.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    @Operation(
            summary = "Get all users",
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

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by it's own id",
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
            summary = "Creation of new user",
            description = "Creation of new user"
    )
    public UserDTO createUser(@Valid
                              @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                      description = "User data, required for creation",
                                      required = true,
                                      content = @Content(schema = @Schema(implementation = UserDTO.class))
                              ) @RequestBody UserDTO userDTO) {
        return crudUserService.create(userDTO);
    }

    @PutMapping("/{id}")
    public void updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO, @PathVariable Long id) {
        crudUserService.update(userUpdateDTO, id);
    }

    @PutMapping("/{id}/password")
    public void updatePassword(@Valid @RequestBody PasswordUpdateDTO passwordUpdateDTO, @PathVariable Long id) {
        crudUserService.updatePassword(passwordUpdateDTO, id);
    }

    @DeleteMapping("/{id}")
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
    public void deleteUser(@Parameter(
            description = "ID of the user",
            required = true
    ) @PathVariable Long id) {
        crudUserService.delete(id);
    }

}
