package com.tutorialsejong.courseregistration.auth.dto;

public record LoginResponse(
        String accessToken,
        String username) {
}
