package com.tutorialsejong.courseregistration.common.security;

import com.tutorialsejong.courseregistration.common.exception.ErrorResponse;
import com.tutorialsejong.courseregistration.common.exception.GlobalErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class SwaggerAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=\"Tutorial Sejong Swagger\"");
        ErrorResponse.from(GlobalErrorCode.UNAUTHORIZED)
                .writeTo(response);
    }
}
