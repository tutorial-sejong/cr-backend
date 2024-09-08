package com.tutorialsejong.courseregistration.domain.auth.dto;

public record AuthenticationResult(
        String accessToken,
        String refreshToken,
        String username
) {
}
