package com.tutorialsejong.courseregistration.domain.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

public record ScheduleNotFoundResponse(
        @Schema(description = "에러 코드", defaultValue = "S001")
        String code,
        @Schema(description = "메세지", defaultValue = "존재하지 않는 강의입니다.")
        String message
) {
}
