package com.tutorialsejong.courseregistration.domain.wishlist.exception;

import com.tutorialsejong.courseregistration.common.exception.BusinessException;

public class WishlistCourseAlreadyRegisteredException extends BusinessException {

    public WishlistCourseAlreadyRegisteredException() {
        super(WishlistErrorCode.WISHLIST_COURSE_ALREADY_REGISTERED);
    }
}
