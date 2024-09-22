package com.tutorialsejong.courseregistration.domain.schedule.exception;

import com.tutorialsejong.courseregistration.common.exception.BusinessException;

public class ScheduleBadRequestException extends BusinessException {

    public ScheduleBadRequestException() {
        super(ScheduleErrorCode.BAD_REQUEST);
    }
}
