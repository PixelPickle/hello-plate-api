package com.squirrelly_app.hello_plate_api.exception;

public class InvalidRecipeId extends RuntimeException {
    public InvalidRecipeId(String message) {
        super(message);
    }
}
