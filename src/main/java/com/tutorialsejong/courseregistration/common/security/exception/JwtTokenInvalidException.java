package com.tutorialsejong.courseregistration.common.security.exception;

public class JwtTokenInvalidException extends JwtException {

    public JwtTokenInvalidException() {
        super(SecurityErrorCode.JWT_TOKEN_INVALID);
    }
}
