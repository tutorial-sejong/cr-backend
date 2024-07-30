package com.tutorialsejong.courseregistration.auth.dto;

public record JwtTokens(
        String accessToken,
        String refreshToken) {
}
