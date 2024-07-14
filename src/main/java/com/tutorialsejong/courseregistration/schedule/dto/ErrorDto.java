package com.tutorialsejong.courseregistration.schedule.dto;

import java.util.Date;

public record ErrorDto(Date timestamp, Integer statusCode, String message, String details) {
}
