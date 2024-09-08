package com.tutorialsejong.courseregistration.domain.registration.exception;

import com.tutorialsejong.courseregistration.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CourseRegistrationErrorCode implements ErrorCode {

    COURSE_ALREADY_REGISTERED("C001", "이미 수강신청된 과목입니다.", HttpStatus.CONFLICT),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
