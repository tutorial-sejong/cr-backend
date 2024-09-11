package com.tutorialsejong.courseregistration.domain.user.repository;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}
