package com.tutorialsejong.courseregistration.common.security;

import com.tutorialsejong.courseregistration.common.exception.ErrorResponse;
import com.tutorialsejong.courseregistration.common.exception.GlobalErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class SwaggerAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.addHeader("WWW-Authenticate", "Basic realm=\"Tutorial Sejong Swagger\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorResponse.from(GlobalErrorCode.UNAUTHORIZED)
                .writeTo(response);
    }
}