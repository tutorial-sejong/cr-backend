package com.tutorialsejong.courseregistration.domain.registration.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CourseRegistrationResponse(

        @Schema(description = "학번", example = "12345678911")
        String studentId,

        @Schema(description = "과목 ID", example = "1")
        Long scheduleId
) {
}
