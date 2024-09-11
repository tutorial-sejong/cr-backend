package com.tutorialsejong.courseregistration.domain.registration.exception;

import com.tutorialsejong.courseregistration.common.exception.BusinessException;

public class CourseAlreadyRegisteredException extends BusinessException {

    public CourseAlreadyRegisteredException() {
        super(CourseRegistrationErrorCode.COURSE_ALREADY_REGISTERED);
    }
}
