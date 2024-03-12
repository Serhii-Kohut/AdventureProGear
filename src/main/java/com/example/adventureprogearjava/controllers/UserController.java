package com.example.adventureprogearjava.controllers;

import com.example.adventureprogearjava.dto.UserDTO;
import com.example.adventureprogearjava.services.impl.CRUDUserServiceImpl;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "User Controller",
        description = "API operations with users")
public class UserController {

    CRUDUserServiceImpl crudUserService;

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
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid data",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Operation(
            summary = "Update of the user",
            description = "Update of the user"
    )
    public void updateUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User data, required for creation",
            required = true,
            content = @Content(schema = @Schema(implementation = UserDTO.class))
    ) @Valid @RequestBody UserDTO userDTO,
                           @Parameter(
                                   description = "ID of the user",
                                   required = true
                           ) @PathVariable Long id) {
        crudUserService.update(userDTO, id);
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
