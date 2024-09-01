package com.tutorialsejong.courseregistration.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

public record ErrorResponse(
        @JsonUnwrapped
        ErrorCode errorCode,
 
        @JsonInclude(Include.NON_EMPTY)
        List<InvalidParam> invalidParams
) {

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode, null);
    }

    public static ErrorResponse of(ErrorCode errorCode, List<InvalidParam> invalidParams) {
        return new ErrorResponse(errorCode, invalidParams);
    }

    public ResponseEntity<Object> asHttp() {
        return ResponseEntity.status(errorCode.getStatus()).body(this);
    }

    public record InvalidParam(String name, String reason) {

        public static InvalidParam from(FieldError fieldError) {
            return new InvalidParam(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
