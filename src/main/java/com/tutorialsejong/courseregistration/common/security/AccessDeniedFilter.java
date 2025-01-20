package com.tutorialsejong.courseregistration.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import com.tutorialsejong.courseregistration.common.exception.ErrorResponse;
import com.tutorialsejong.courseregistration.common.exception.GlobalErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class AccessDeniedFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

    // 스웨거 패턴 (필요에 맞게 수정)
    private static final String[] SWAGGER_PATTERNS = {
            "/swagger-ui/**", "/v3/api-docs/**"
    };

    /**
     * 스웨거 요청은 AccessDeniedFilter를 거치지 않도록 설정
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        for (String pattern : SWAGGER_PATTERNS) {
            if (servletPath.startsWith("/swagger-ui") || servletPath.startsWith("/v3/api-docs")) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException ex) {
            // 접근 거부 시 커스텀 예외 응답 처리
            handleAccessDenied(response, ex);
        } catch (Exception e) {
            // 기타 예외도 여기서 처리 가능
            handleException(response, e);
        }
    }

    private void handleAccessDenied(HttpServletResponse response, AccessDeniedException ex)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // 원하는 JSON 구조에 맞춰서 응답
        ErrorResponse errorResponse = ErrorResponse.from(GlobalErrorCode.HANDLE_ACCESS_DENIED);
        errorResponse.writeTo(response);
        String body = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(body);
    }

    private void handleException(HttpServletResponse response, Exception ex)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        ErrorResponse errorResponse = ErrorResponse.from(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        errorResponse.writeTo(response);
        String body = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(body);
    }
}