package com.tutorialsejong.courseregistration.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "studentId should not be empty")
        String studentId,

        @NotBlank(message = "password should not be empty")
        String password
) {
}
