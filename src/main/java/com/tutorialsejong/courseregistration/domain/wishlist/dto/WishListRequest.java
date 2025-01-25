package com.tutorialsejong.courseregistration.domain.wishlist.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record WishListRequest(
        @Schema(description = "11자리 이상 학번", example = "12345678911")
        String studentId,

        @Schema(description = "강의 ID", example = "1")
        Long scheduleId
) {
}
