package com.chubaievskyi.controller;

import com.chubaievskyi.dto.ErrorResponseDto;
import com.chubaievskyi.dto.PageDto;
import com.chubaievskyi.dto.UserDto;
import com.chubaievskyi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@CrossOrigin(origins = {})
@SecurityRequirement(name = "todo-app")
@Tag(name = "Users")
@RequiredArgsConstructor
@RequestMapping("/users")

public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a user.", description = "Creating and adding a new user to the database.",
            parameters = @Parameter(ref = "#/components/parameters/Accept-Language"))
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
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto,
                                              @RequestHeader(name = "Accept-Language") Locale locale) {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @Operation(summary = "Update user by ID.", description = "Update user data.",
            parameters = @Parameter(ref = "#/components/parameters/Accept-Language"))
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
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto,
                                              @RequestHeader(name = "Accept-Language") Locale locale) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Operation(summary = "Update password by user email.", description = "Update password.",
            parameters = @Parameter(ref = "#/components/parameters/Accept-Language"))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204", description = "Password successfully updated.",
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
    @PutMapping("/updatePassword/{email}")
    public ResponseEntity<UserDto> updateOwnUserPassword(@PathVariable String email,
                                                         String currentPassword, String newPassword,
                                                         @RequestHeader(name = "Accept-Language") Locale locale) {
        UserDto updatedUserPasswordDto = userService.updateOwnUserPassword(email, currentPassword, newPassword);
        return new ResponseEntity<>(updatedUserPasswordDto, HttpStatus.OK);
    }

    @Operation(summary = "Delete user by ID.", description = "Delete user from database.",
            parameters = @Parameter(ref = "#/components/parameters/Accept-Language"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successfully deleted.", content = @Content),
            @ApiResponse(
                    responseCode = "400", description = "Invalid data entered.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id,
                                           @RequestHeader(name = "Accept-Language") Locale locale) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get user by ID.", description = "Returns user by id",
            parameters = @Parameter(ref = "#/components/parameters/Accept-Language"))
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
    public ResponseEntity<UserDto> findUserById(@Parameter(description = "User Id") @PathVariable Long id,
                                                @RequestHeader(name = "Accept-Language") Locale locale) {

        UserDto userDto = userService.findUserById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @Operation(summary = "Get user by email.", description = "Returns user by email",
            parameters = @Parameter(ref = "#/components/parameters/Accept-Language"))
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
    @GetMapping("/by-email/{email}")
    public ResponseEntity<UserDto> findUserByEmail(@Parameter(description = "User email") @PathVariable String email,
                                                   @RequestHeader(name = "Accept-Language") Locale locale) {
        UserDto userDto = userService.findUserByEmail(email);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @Operation(summary = "Get all users.", description = "Returns all users",
            parameters = @Parameter(ref = "#/components/parameters/Accept-Language"))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Success. The users has been returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageDto.class))),
    })
    @GetMapping
    public ResponseEntity<PageDto<UserDto>> findAllUsers(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestHeader(name = "Accept-Language") Locale locale) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("email").ascending());
        Page<UserDto> userDtoPage = userService.findAllUsers(pageable);
        PageDto<UserDto> response = new PageDto<>(userDtoPage.getContent(), page, userDtoPage.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}