package com.hhplus.clean_architecture.domain.exception;

public class LectureFullException extends RuntimeException {

    private final String errorString;

    public LectureFullException(String message) {
        super(message);
        this.errorString = "E500";
    }

    public String getErrorString() {
        return errorString;
    }
}
