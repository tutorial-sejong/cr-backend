package com.tutorialsejong.courseregistration.auth.dto;

public record AuthenticationResult(
        String accessToken,
        String refreshToken,
        String username
) {
}
