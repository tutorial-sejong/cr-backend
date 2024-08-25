package com.tutorialsejong.courseregistration.domain.schedule.dto;

import java.util.Date;

public record ErrorDto(Date timestamp, Integer statusCode, String message, String details) {
}
