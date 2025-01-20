package com.tutorialsejong.courseregistration.common.security;

import com.tutorialsejong.courseregistration.common.exception.ErrorResponse;
import com.tutorialsejong.courseregistration.common.exception.GlobalErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class SwaggerAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ErrorResponse.from(GlobalErrorCode.HANDLE_ACCESS_DENIED)
                .writeTo(response);
    }
}
