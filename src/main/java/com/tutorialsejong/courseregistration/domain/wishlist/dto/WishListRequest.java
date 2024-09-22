package com.tutorialsejong.courseregistration.domain.wishlist.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record WishListRequest(
        @Schema(description = "11자리 이상 학번", example = "12345678911")
        @NotBlank(message = "studentId should not be empty")
        String studentId,
        @Schema(description = "과목 번호", example = "1")
        @NotBlank(message = "scheduleId should not be empty")
        Long scheduleId
) {
}
