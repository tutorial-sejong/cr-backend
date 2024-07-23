package com.tutorialsejong.courseregistration.wishlist.dto;


import java.util.List;

public record WishListRequest(
        String studentId,
        List<Long> wishListIdList
) {
}