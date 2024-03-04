package com.chubaievskyi.exception;

import com.chubaievskyi.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String DATE_TIME_FORMAT = "HH:mm:ss dd.MM.yyyy";

    @ExceptionHandler(value = {UserNotFoundException.class, TaskNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(RuntimeException e) {
        String message = e instanceof UserNotFoundException ? "User not found" : "Task not found";

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
                HttpStatus.NOT_FOUND.value(),
                "Not found",
                message);

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException e) {

        List<String> validationErrors = new ArrayList<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            validationErrors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                validationErrors.toString());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidPasswordException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidPasswordException(RuntimeException e) {
        String message = "Incorrect password entered: ";

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
                HttpStatus.BAD_REQUEST.value(),
                "BAD REQUEST",
                message);

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(RuntimeException e) {
        String message = "Incorrect status entered: ";

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
                HttpStatus.NOT_FOUND.value(),
                "Not found",
                message);

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalStateException(RuntimeException e) {
        String message = "Invalid status transition";

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
                HttpStatus.NOT_FOUND.value(),
                "BAD REQUEST",
                message);

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }
}