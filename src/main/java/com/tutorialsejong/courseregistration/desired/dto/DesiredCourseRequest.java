package com.tutorialsejong.courseregistration.desired.dto;


import java.util.List;

public record DesiredCourseRequest(
        String studentId,
        List<CourseInformation> desiredCourseList
) {
}