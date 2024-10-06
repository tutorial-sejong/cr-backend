package com.tutorialsejong.courseregistration.domain.auth.dto;

public record LoginResponse(
        String accessToken,
        String username
) {
}
