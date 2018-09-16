package com.swiftcode.web.exceptions;

public class UserNotFountException extends RuntimeException {
    public UserNotFountException() {
        super();
    }

    public UserNotFountException(String message) {
        super(message);
    }

    public UserNotFountException(String message, Throwable cause) {
        super(message, cause);
    }
}
