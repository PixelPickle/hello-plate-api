package com.squirrelly_app.hello_plate_api.exception;

public class RequiredValueException extends RuntimeException {
    public RequiredValueException(String message) {
        super(message);
    }
}
