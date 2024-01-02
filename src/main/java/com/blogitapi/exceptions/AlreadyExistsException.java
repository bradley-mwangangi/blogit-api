package com.blogitapi.exceptions;

import jakarta.persistence.EntityExistsException;
import jakarta.validation.constraints.NotNull;

public class AlreadyExistsException extends EntityExistsException {

    public AlreadyExistsException(
            @NotNull String field,
            @NotNull String email
    ) {
        super("Already exists: " + field + ": " + email);
    }
}
