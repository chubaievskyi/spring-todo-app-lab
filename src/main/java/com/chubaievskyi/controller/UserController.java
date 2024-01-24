package com.chubaievskyi.controller;

import com.chubaievskyi.dto.PageDto;
import com.chubaievskyi.dto.UserDto;
import com.chubaievskyi.dto.ErrorResponseDto;
import com.chubaievskyi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @Operation(summary = "Create a user.", description = "Creating and adding a new user to the database.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "User successfully created.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(
                    responseCode = "400", description = "Invalid data entered.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @Operation(summary = "Update user by ID.", description = "Update user data.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204", description = "User successfully updated.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(
                    responseCode = "400", description = "Invalid data entered.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "404", description = "User not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Operation(summary = "Delete user by ID.", description = "Delete user from database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successfully deleted.", content = @Content),
            @ApiResponse(
                    responseCode = "400", description = "Invalid data entered.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get user by ID", description = "Returns user by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Success. The user has been returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(
                    responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@Parameter(description = "User Id") @PathVariable Long id) {
        UserDto userDto = userService.findUserById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @Operation(summary = "Get all users", description = "Returns user by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Success. The user has been returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageDto.class))),
    })
    @GetMapping
    public ResponseEntity<PageDto<UserDto>> findAllUsers(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("firstName").ascending());
        Page<UserDto> userDtoPage = userService.findAllUsers(pageable);
        PageDto<UserDto> response = new PageDto<>(userDtoPage.getContent(), page, userDtoPage.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}