package com.tutorialsejong.courseregistration.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MacroResponse(
        @Schema(description = "상태 코드", example = "200")
        int statusCode,

        @Schema(description = "캡차 데이터")
        CaptchaResult data
) {
}
