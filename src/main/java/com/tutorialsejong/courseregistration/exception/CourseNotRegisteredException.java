package com.tutorialsejong.courseregistration.exception;

public class CourseNotRegisteredException extends RuntimeException {
    public CourseNotRegisteredException(String message) {
        super(message);
    }
}
