package com.tutorialsejong.courseregistration.common.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = BEARER_PREFIX.length();

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        extractJwtFromRequest(request)
                .ifPresent(jwt -> processJwtAuthentication(jwt, request));

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return Optional.of(bearerToken.substring(BEARER_PREFIX_LENGTH));
        }
        return Optional.empty();
    }

    private void processJwtAuthentication(String jwt, HttpServletRequest request) {
        tokenProvider.validateToken(jwt);
        Authentication authentication = tokenProvider.getAuthentication(jwt);
        enhanceAuthenticationWithRequestDetails(authentication, request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void enhanceAuthenticationWithRequestDetails(Authentication authentication, HttpServletRequest request) {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            ((UsernamePasswordAuthenticationToken) authentication)
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/api/auth/login") || path.equals("/api/auth/refresh") || path.equals("/api/auth/withdraw") || path.equals("/docs/swagger-ui/**") || path.equals("/v3/api-docs/**");
    }
}
