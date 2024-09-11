package com.tutorialsejong.courseregistration.domain.wishlist.dto;


public record WishListRequest(
        String studentId,
        Long scheduleId
) {
}
