package com.tutorialsejong.courseregistration.domain.wishlist.exception;

import com.tutorialsejong.courseregistration.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WishlistErrorCode implements ErrorCode {

    ALREADY_IN_WISHLIST("W001", "이미 관심과목 담기된 과목입니다.", HttpStatus.CONFLICT),
    WISHLIST_COURSE_ALREADY_REGISTERED("W002", "이미 수강신청된 과목은 관심과목으로 담을 수 없습니다.", HttpStatus.CONFLICT),
    WISHLIST_NOT_FOUND("W003", "존재하지 않는 과목입니다.", HttpStatus.NOT_FOUND),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
