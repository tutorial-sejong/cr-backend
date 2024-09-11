package com.tutorialsejong.courseregistration.common.security.exception;

import com.tutorialsejong.courseregistration.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SecurityErrorCode implements ErrorCode {

    AUTHENTICATION_FAILED("S001", "인증에 실패했습니다.", HttpStatus.UNAUTHORIZED),
    JWT_TOKEN_EXPIRED("S002", "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    JWT_TOKEN_INVALID("S003", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
