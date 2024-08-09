package com.tutorialsejong.courseregistration.wishlist.dto;


public record WishListRequest(
        String studentId,
        Long scheduleId
) {
}
