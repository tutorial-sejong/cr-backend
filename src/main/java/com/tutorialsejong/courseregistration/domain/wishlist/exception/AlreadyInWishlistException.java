package com.tutorialsejong.courseregistration.domain.wishlist.exception;

import com.tutorialsejong.courseregistration.common.exception.BusinessException;

public class AlreadyInWishlistException extends BusinessException {

    public AlreadyInWishlistException() {
        super(WishlistErrorCode.ALREADY_IN_WISHLIST);
    }
}
