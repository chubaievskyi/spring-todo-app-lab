package com.chubaievskyi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

@Schema(description = "Task DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDto {

    @Schema(description = "Task ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @Schema(description = "Created at", example = "2024-02-18 16:52:43", accessMode = Schema.AccessMode.READ_ONLY)
    String created;

    @Schema(description = "Task name", example = "Task 1")
    @NotBlank
    @Size(min = 3, max = 100, message = "The task must contain between 3 and 100 characters")
    String name;

    @Schema(description = "Description", example = "Do something")
    @NotBlank
    @Size(min = 3, max = 100, message = "The description must contain between 3 and 100 characters")
    String description;

    @Schema(description = "User name", example = "Rod@gmail.com")
    @NotBlank
    @Size(min = 2, max = 100, message = "The user name must contain between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯґҐєЄіІїЇ]*$", message = "User name should contain only Ukrainian or English letters.")
    String owner;

    @Schema(description = "Deadline", example = "2024-02-29")
    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    String deadline;

    @Schema(description = "Task status", example = "NEW", accessMode = Schema.AccessMode.READ_ONLY)
    String status;
}