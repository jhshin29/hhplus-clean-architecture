package com.hhplus.clean_architecture.exception;

public class AlreadyEnrollmentException extends RuntimeException {

    private final String errorString;

    public AlreadyEnrollmentException(String errorString, String message) {
        super(message);
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
