package com.chubaievskyi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("app.user.not.found.id" + id);
    }

    public UserNotFoundException(String email) {
        super("app.user.not.found.email" + email);
    }
}