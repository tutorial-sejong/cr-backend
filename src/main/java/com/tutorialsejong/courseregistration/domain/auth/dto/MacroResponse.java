package com.tutorialsejong.courseregistration.domain.auth.dto;

public record MacroResponse(
        int statusCode,
        CaptchaResult data
) {
}
