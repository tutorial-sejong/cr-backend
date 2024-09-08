package com.tutorialsejong.courseregistration.domain.user.exception;

import com.tutorialsejong.courseregistration.common.exception.BusinessException;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}
