package com.tutorialsejong.courseregistration.common.security.exception;

public class AuthenticationFailedException extends JwtException {

    public AuthenticationFailedException() {
        super(SecurityErrorCode.AUTHENTICATION_FAILED);
    }
}
