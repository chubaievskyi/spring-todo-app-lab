package com.chubaievskyi.exception;

import com.chubaievskyi.entity.Event;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidStatusException extends IllegalStateException {

    public InvalidStatusException(String status) {
        super(status);
    }
}