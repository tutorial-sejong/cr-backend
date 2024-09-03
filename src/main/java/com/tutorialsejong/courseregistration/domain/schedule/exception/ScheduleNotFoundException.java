package com.tutorialsejong.courseregistration.domain.schedule.exception;

import com.tutorialsejong.courseregistration.common.exception.BusinessException;

public class ScheduleNotFoundException extends BusinessException {

    public ScheduleNotFoundException() {
        super(ScheduleErrorCode.SCHEDULE_NOT_FOUND);
    }
}
