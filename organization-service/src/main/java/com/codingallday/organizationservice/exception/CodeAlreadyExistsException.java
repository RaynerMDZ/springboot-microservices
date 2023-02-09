package com.codingallday.organizationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CodeAlreadyExistsException extends RuntimeException {
    private String message;

    public CodeAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
}
