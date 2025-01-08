package com.tutorialsejong.courseregistration.domain.auth.dto;

public record CaptchaResult(
        String answer,
        String url
) {
}
