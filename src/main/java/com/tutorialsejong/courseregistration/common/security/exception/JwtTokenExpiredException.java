package com.tutorialsejong.courseregistration.common.security.exception;

public class JwtTokenExpiredException extends JwtException {

    public JwtTokenExpiredException() {
        super(SecurityErrorCode.JWT_TOKEN_EXPIRED);
    }
}
