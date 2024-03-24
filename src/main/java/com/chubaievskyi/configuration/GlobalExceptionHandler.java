package com.chubaievskyi.configuration;

import com.chubaievskyi.dto.ErrorResponseDto;
import com.chubaievskyi.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final String DATE_TIME_FORMAT = "HH:mm:ss dd.MM.yyyy";
    private static final String BAD_REQUEST = "BAD REQUEST";
    private final MessageSource messageSource;

    @ExceptionHandler(value = {UserNotFoundException.class, TaskNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(RuntimeException e, Locale locale) {

        String messageKey = e instanceof UserNotFoundException ? "app.user.not.found.id": "app.task.not.found.id";
        String message = messageSource.getMessage(messageKey, null, locale);

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
                HttpStatus.NOT_FOUND.value(),
                messageSource.getMessage("app.not.found", null, locale),
                message + e.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidPasswordException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidPasswordException(RuntimeException e, Locale locale) {

        String message = messageSource.getMessage("app.incorrect.password", null, locale);

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
                HttpStatus.BAD_REQUEST.value(),
                BAD_REQUEST,
                message + e.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidStatusException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidStatusException(RuntimeException e, Locale locale) {

        String message = messageSource.getMessage("app.status.invalid", null, locale);

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
                HttpStatus.BAD_REQUEST.value(),
                BAD_REQUEST,
                message + e.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AccessTaskException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessTaskException(RuntimeException e, Locale locale) {

        String message = messageSource.getMessage("app.task.access.denied", null, locale);

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
                HttpStatus.BAD_REQUEST.value(),
                BAD_REQUEST,
                message + e.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(RuntimeException e) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
                HttpStatus.BAD_REQUEST.value(),
                BAD_REQUEST,
                e.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }
}