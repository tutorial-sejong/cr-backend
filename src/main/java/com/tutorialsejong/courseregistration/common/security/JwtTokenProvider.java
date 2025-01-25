package com.tutorialsejong.courseregistration.common.security;

import com.tutorialsejong.courseregistration.common.security.exception.JwtTokenExpiredException;
import com.tutorialsejong.courseregistration.common.security.exception.JwtTokenInvalidException;
import com.tutorialsejong.courseregistration.common.utils.log.LogAction;
import com.tutorialsejong.courseregistration.common.utils.log.LogMessage;
import com.tutorialsejong.courseregistration.common.utils.log.LogReason;
import com.tutorialsejong.courseregistration.common.utils.log.LogResult;
import com.tutorialsejong.courseregistration.domain.auth.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final Key key;
    private final int accessTokenExpirationInMs;
    private final int refreshTokenExpirationInMs;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtTokenProvider(
            @Value("${app.jwt.secret}") String jwtSecret,
            @Value("${app.jwt.accessTokenExpirationInMs}") int accessTokenExpirationInMs,
            @Value("${app.jwt.refreshTokenExpirationInMs}") int refreshTokenExpirationInMs,
            CustomUserDetailsService customUserDetailsService) {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.accessTokenExpirationInMs = accessTokenExpirationInMs;
        this.refreshTokenExpirationInMs = refreshTokenExpirationInMs;
        this.customUserDetailsService = customUserDetailsService;
    }

    public String generateAccessToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return generateToken(userDetails.getUsername(), accessTokenExpirationInMs);
    }

    public String generateRefreshToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return generateToken(userDetails.getUsername(), refreshTokenExpirationInMs);
    }

    private String generateToken(String username, int expirationInMs) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String generateAccessTokenFromUsername(String username) {
        return generateToken(username, accessTokenExpirationInMs);
    }

    public String getUsernameFromJWT(String token) {
        validateToken(token);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (ExpiredJwtException ex) {
            String username = ex.getClaims().getSubject();
            logger.warn(LogMessage.builder()
                    .action(LogAction.VALIDATE_TOKEN)
                    .subject("s" + username)
                    .result(LogResult.FAIL)
                    .reason(LogReason.EXPIRED)
                    .build().toString());
            throw new JwtTokenExpiredException();
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            String username = getUsernameFromJWT(token);
            logger.warn(LogMessage.builder()
                    .action(LogAction.VALIDATE_TOKEN)
                    .subject("s" + username)
                    .result(LogResult.FAIL)
                    .reason(LogReason.INVALID_CREDENTIAL)
                    .build().toString());
            throw new JwtTokenInvalidException();
        }
    }

    public Authentication getAuthentication(String token) {
        String username = getUsernameFromJWT(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
