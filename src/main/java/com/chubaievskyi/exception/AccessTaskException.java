package com.chubaievskyi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessTaskException extends RuntimeException {
    public AccessTaskException(Long message) {
        super(String.valueOf(message));
    }
}