package com.tutorialsejong.courseregistration.domain.auth.exception;

import com.tutorialsejong.courseregistration.common.exception.BusinessException;

public class InvalidLoginException extends BusinessException {
    
    public InvalidLoginException() {
        super(AuthErrorCode.AUTHENTICATION_FAILED);
    }
}
