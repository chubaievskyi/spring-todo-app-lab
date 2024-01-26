package com.chubaievskyi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "User DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Schema(description = "User ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Username", example = "Rod")
    @NotNull
    @Size(min = 2, max = 100, message = "The username must contain between 2 and 100 characters")
//    @Pattern(regexp = "^[a-zA-Zа-яА-ЯґҐєЄіІїЇ]*$", message = "Username should contain only Ukrainian or English letters.")
    private String username;

    @Schema(description = "User password", example = "123")
    @NotNull
    @Size(min = 3, max = 100, message = "The password must contain between 3 and 100 characters")
    private String password;

    @Schema(description = "User role", example = "ADMIN")
    private String role;
}