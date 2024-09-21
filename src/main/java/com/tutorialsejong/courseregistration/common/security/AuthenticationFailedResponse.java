package com.tutorialsejong.courseregistration.common.security;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthenticationFailedResponse(
        @Schema(description = "에러 코드", defaultValue = "S001")
        String code,
        @Schema(description = "메세지", defaultValue = "인증에 실패했습니다.")
        String message
) {
}
