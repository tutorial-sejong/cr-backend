package com.tutorialsejong.courseregistration.domain.wishlist.exception;

import com.tutorialsejong.courseregistration.common.exception.BusinessException;
import com.tutorialsejong.courseregistration.common.exception.ErrorCode;

public class AlreadyInWishlistException extends BusinessException {

    public AlreadyInWishlistException() {
        super(ErrorCode.ALREADY_IN_WISHLIST);
    }
}
