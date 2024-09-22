package com.tutorialsejong.courseregistration.domain.schedule.exception;

import com.tutorialsejong.courseregistration.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ScheduleErrorCode implements ErrorCode {

    SCHEDULE_NOT_FOUND("S001", "존재하지 않는 강의입니다.", HttpStatus.NOT_FOUND),
    BAD_REQUEST("S002", "유효하지 않은 Parameter 입니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
