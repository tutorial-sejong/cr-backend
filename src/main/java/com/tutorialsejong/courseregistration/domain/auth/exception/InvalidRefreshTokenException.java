package com.tutorialsejong.courseregistration.domain.auth.exception;

import com.tutorialsejong.courseregistration.common.exception.BusinessException;

public class InvalidRefreshTokenException extends BusinessException {

    public InvalidRefreshTokenException() {
        super(AuthErrorCode.INVALID_REFRESH_TOKEN);
    }
}
