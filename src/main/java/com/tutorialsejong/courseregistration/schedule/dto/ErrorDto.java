package com.tutorialsejong.courseregistration.schedule.dto;

import java.util.Date;

public record ErrorDto(Date timestamp, String message, String details) {
}
