package com.tutorialsejong.courseregistration.exception;

public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
