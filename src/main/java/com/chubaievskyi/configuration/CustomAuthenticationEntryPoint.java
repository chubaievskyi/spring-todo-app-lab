package com.chubaievskyi.configuration;

import com.chubaievskyi.dto.ErrorResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy")),
                HttpStatus.FORBIDDEN.value(),
                "FORBIDDEN",
                "Not authorized.");

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        mapper.writeValue(response.getOutputStream(), errorResponseDto);
    }
}