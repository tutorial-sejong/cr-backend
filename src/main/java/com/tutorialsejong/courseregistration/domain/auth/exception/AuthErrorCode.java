package com.tutorialsejong.courseregistration.domain.auth.exception;

import com.tutorialsejong.courseregistration.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    AUTHENTICATION_FAILED("A001", "아이디 또는 비밀번호가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN("A002", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
