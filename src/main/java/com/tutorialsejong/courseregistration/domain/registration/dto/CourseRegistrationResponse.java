package com.tutorialsejong.courseregistration.domain.registration.dto;

public record CourseRegistrationResponse(
        String studentId,
        Long scheduleId
) {
}
