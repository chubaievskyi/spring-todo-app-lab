package com.chubaievskyi.controller;

import com.chubaievskyi.dto.ErrorResponseDto;
import com.chubaievskyi.dto.PageDto;
import com.chubaievskyi.dto.TaskDto;
import com.chubaievskyi.entity.Status;
import com.chubaievskyi.service.TaskService;
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

@RestController
@CrossOrigin(origins = {})
@SecurityRequirement(name = "todo-app")
@Tag(name = "Tasks")
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Create a task.", description = "Creating and adding a new task to the database.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Task successfully created.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(
                    responseCode = "400", description = "Invalid data entered.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto) {
        TaskDto createdTask = taskService.createTask(taskDto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }


    @Operation(summary = "Update task by ID.", description = "Update task data.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204", description = "Task successfully updated.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(
                    responseCode = "400", description = "Invalid data entered.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "404", description = "Task not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDto taskDto) {
        TaskDto updatedTask = taskService.updateTask(id, taskDto);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @Operation(summary = "Update task status by ID.", description = "Update task status.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204", description = "Task status successfully updated.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(
                    responseCode = "400", description = "Invalid data entered.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "404", description = "Task not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/status/{id}")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable Long id, @Valid @RequestBody Status status) {
        TaskDto updatedTaskStatus = taskService.updateTaskStatus(id, status);
        return new ResponseEntity<>(updatedTaskStatus, HttpStatus.OK);
    }

    @Operation(summary = "Delete task by ID.", description = "Delete task from database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task successfully deleted.", content = @Content),
            @ApiResponse(
                    responseCode = "400", description = "Invalid data entered.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get task by ID.", description = "Returns task by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Success. The task has been returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(
                    responseCode = "404", description = "Task not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> findTaskById(@Parameter(description = "Task Id") @PathVariable Long id) {
        TaskDto taskDto = taskService.findTaskById(id);
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }

    @Operation(summary = "Get all tasks.", description = "Returns all tasks")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Success. The tasks has been returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageDto.class))),
    })
    @GetMapping
    public ResponseEntity<PageDto<TaskDto>> findAllTasks(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<TaskDto> taskDtoPage = taskService.findAllTasks(pageable);
        PageDto<TaskDto> response = new PageDto<>(taskDtoPage.getContent(), page, taskDtoPage.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}