package com.tutorialsejong.courseregistration.domain.wishlist.exception;

import com.tutorialsejong.courseregistration.common.exception.BusinessException;
import com.tutorialsejong.courseregistration.common.exception.ErrorCode;

public class WishlistCourseAlreadyRegisteredException extends BusinessException {

    public WishlistCourseAlreadyRegisteredException() {
        super(ErrorCode.WISHLIST_COURSE_ALREADY_REGISTERED);
    }
}
