package com.tutorialsejong.courseregistration.domain.registration.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CourseAlreadyRegisteredResponse(

        @Schema(description = "에러 코드", defaultValue = "R001")
        String code,
        @Schema(description = "메세지", defaultValue = "이미 수강신청된 과목입니다.")
        String message
) {
}