package com.tutorialsejong.courseregistration.domain.schedule.dto;

import com.tutorialsejong.courseregistration.domain.schedule.exception.ScheduleErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

public record ScheduleBadRequestResponse(
        @Schema(description = "에러 코드", defaultValue = "S002")
        String code,
        @Schema(description = "메세지", defaultValue = "유효하지 않은 Parameter 입니다.")
        String message
) {
}
