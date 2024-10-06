package com.tutorialsejong.courseregistration.domain.auth.dto;

public record JwtTokens(
        String accessToken,
        String refreshToken
) {
}
