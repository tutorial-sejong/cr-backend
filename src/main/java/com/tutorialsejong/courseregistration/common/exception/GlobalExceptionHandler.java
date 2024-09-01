package com.tutorialsejong.courseregistration.common.exception;

import io.jsonwebtoken.ExpiredJwtException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return ErrorResponse.from(errorCode).asHttp();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<ErrorResponse.InvalidParam> invalidParams = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.InvalidParam::from)
                .toList();

        return ErrorResponse.of(GlobalErrorCode.INVALID_INPUT_VALUE, invalidParams).asHttp();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "올바르지 않은 비밀번호입니다!");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", Collections.singletonList(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<?> handleJwtAuthenticationException(JwtAuthenticationException ex) {
        Map<String, Object> body = new HashMap<>();
        if (ex.getCause() instanceof ExpiredJwtException) {
            body.put("message", Collections.singletonList("토큰이 만료되었습니다."));
        } else {
            body.put("message", Collections.singletonList("유효하지 않은 토큰입니다."));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(CheckUserException.class)
    public ResponseEntity<?> handleCheckUserException(CheckUserException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CourseNotRegisteredException.class)
    public ResponseEntity<?> handleCourseNotRegisteredException(CourseNotRegisteredException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "An unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
