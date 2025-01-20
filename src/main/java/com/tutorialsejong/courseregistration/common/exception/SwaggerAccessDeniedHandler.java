package com.tutorialsejong.courseregistration.common.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import java.io.IOException;

public class SwaggerAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // ErrorResponse를 SecurityErrorCode 또는 GlobalErrorCode 등 원하는 ErrorCode 를 사용하여 생성
        ErrorResponse.from(GlobalErrorCode.HANDLE_ACCESS_DENIED)
                .writeTo(response);
    }
}