package com.tutorialsejong.courseregistration.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Schema(description = "11자리 이상 학번", example = "12345678911")
        @NotBlank(message = "studentId should not be empty")
        String studentId,

        @Schema(description = "비밀번호", example = "12345678911")
        @NotBlank(message = "password should not be empty")
        String password
) {
}
