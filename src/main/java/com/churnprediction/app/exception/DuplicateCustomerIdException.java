package com.churnprediction.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateCustomerIdException extends RuntimeException {
    public DuplicateCustomerIdException(String message) {
        super(message);
    }
}
