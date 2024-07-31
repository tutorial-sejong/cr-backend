package com.tutorialsejong.courseregistration.common.exception;

public class CourseNotRegisteredException extends RuntimeException {
    public CourseNotRegisteredException(String message) {
        super(message);
    }
}
