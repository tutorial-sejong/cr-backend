package com.tutorialsejong.courseregistration.domain.wishlist.exception;

import com.tutorialsejong.courseregistration.common.exception.BusinessException;

public class WishlistNotFoundException extends BusinessException {

    public WishlistNotFoundException() {
        super(WishlistErrorCode.WISHLIST_NOT_FOUND);
    }
}
