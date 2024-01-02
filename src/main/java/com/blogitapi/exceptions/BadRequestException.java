package com.blogitapi.exceptions;

import jakarta.validation.constraints.NotNull;

public class BadRequestException extends RuntimeException {

    public BadRequestException(@NotNull String message) {
        super(message);
    }
}
