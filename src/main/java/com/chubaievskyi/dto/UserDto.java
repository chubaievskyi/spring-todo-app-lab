package com.chubaievskyi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Schema(description = "User DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    @Schema(description = "User ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @Schema(description = "Email", example = "Rod@gmail.com")
    @NotBlank
    @Email
    String email;

    @Schema(description = "User password", example = "123")
    @NotBlank
    @Size(min = 3, max = 100, message = "The password must contain between 3 and 100 characters")
    String password;

    @Schema(description = "User first name", example = "Rod")
    @NotBlank
    @Size(min = 2, max = 100, message = "The first name must contain between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯґҐєЄіІїЇ]*$", message = "First name should contain only Ukrainian or English letters.")
    String firstName;

    @Schema(description = "User last name", example = "Johnson")
    @NotBlank
    @Size(min = 2, max = 100, message = "The last name must contain between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯґҐєЄіІїЇ]*$", message = "Last name should contain only Ukrainian or English letters.")
    String lastName;

    @Schema(description = "User role", example = "ADMIN")
    String role;
}