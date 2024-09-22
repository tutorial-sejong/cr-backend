package com.tutorialsejong.courseregistration.domain.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserNotFoundResponse(
        @Schema(description = "에러 코드", defaultValue = "R002")
        String code,
        @Schema(description = "메세지", defaultValue = "존재하지 않는 유저입니다.")
        String message
) {
}
