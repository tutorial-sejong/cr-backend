package com.tutorialsejong.courseregistration.common.security;

import com.tutorialsejong.courseregistration.common.exception.ErrorResponse;
import com.tutorialsejong.courseregistration.common.security.exception.SecurityErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ErrorResponse.from(SecurityErrorCode.AUTHENTICATION_FAILED)
                .writeTo(response);
    }
}
