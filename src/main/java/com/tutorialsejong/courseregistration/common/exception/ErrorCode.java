package com.tutorialsejong.courseregistration.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {

    // Common
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "CO001", "잘못된 입력값입니다."),

    // Course Registration
    COURSE_ALREADY_REGISTERED(HttpStatus.CONFLICT, "CR001", "이미 수강신청된 과목입니다."),

    // Wishlist
    ALREADY_IN_WISHLIST(HttpStatus.CONFLICT, "WL001", "이미 관심과목 담기된 과목입니다."),
    WISHLIST_COURSE_ALREADY_REGISTERED(HttpStatus.CONFLICT, "WL002", "이미 수강신청된 과목은 관심과목으로 담을 수 없습니다."),
    ;

    @JsonIgnore
    private final HttpStatus status;

    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
