package com.alex.applytrackapi.exceptions;

public class ApplicationNotFoundException extends RuntimeException {

    public ApplicationNotFoundException(Long id) {
        super("Application not found with id: " + id);
    }
}
