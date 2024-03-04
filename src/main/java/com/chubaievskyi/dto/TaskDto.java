package com.chubaievskyi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "Task DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDto {

    @Schema(description = "Task ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @Schema(description = "Created at", example = "2024-02-18 16:52:43", accessMode = Schema.AccessMode.READ_ONLY)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;

    @Schema(description = "Created by", example = "user1@gmail.com", accessMode = Schema.AccessMode.READ_ONLY)
    String createdBy;

    @Schema(description = "Task name", example = "Task 1")
    @NotBlank
    @Size(min = 3, max = 100, message = "app.task.name.invalid.size")
    String name;

    @Schema(description = "Description", example = "Do something")
    @NotBlank
    @Size(min = 3, max = 100, message = "app.task.description.invalid.size")
    String description;

    @Schema(description = "User name", example = "user1@gmail.com")
    @Email
    String owner;

    @Schema(description = "Deadline", example = "2024-02-29")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    String deadline;

    @Schema(description = "Task status", example = "NEW", accessMode = Schema.AccessMode.READ_ONLY)
    String status;
}