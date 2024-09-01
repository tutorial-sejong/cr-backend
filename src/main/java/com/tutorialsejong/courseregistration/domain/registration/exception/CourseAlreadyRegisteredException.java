package com.tutorialsejong.courseregistration.domain.registration.exception;

import com.tutorialsejong.courseregistration.common.exception.BusinessException;
import com.tutorialsejong.courseregistration.common.exception.ErrorCode;

public class CourseAlreadyRegisteredException extends BusinessException {

    public CourseAlreadyRegisteredException() {
        super(ErrorCode.COURSE_ALREADY_REGISTERED);
    }
}
