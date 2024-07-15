package com.tutorialsejong.courseregistration.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private final Key accessTokenSecret;
    private final Key refreshTokenSecret;
    private final int accessTokenExpirationInMs;
    private final int refreshTokenExpirationInMs;

    public JwtTokenProvider(
            @Value("${app.jwt.accessTokenSecret}") String accessTokenSecret,
            @Value("${app.jwt.refreshTokenSecret}") String refreshTokenSecret,
            @Value("${app.jwt.accessTokenExpirationInMs}") int accessTokenExpirationInMs,
            @Value("${app.jwt.refreshTokenExpirationInMs}") int refreshTokenExpirationInMs) {
        this.accessTokenSecret = Keys.hmacShaKeyFor(accessTokenSecret.getBytes());
        this.refreshTokenSecret = Keys.hmacShaKeyFor(refreshTokenSecret.getBytes());
        this.accessTokenExpirationInMs = accessTokenExpirationInMs;
        this.refreshTokenExpirationInMs = refreshTokenExpirationInMs;
    }

    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, accessTokenSecret, accessTokenExpirationInMs);
    }

    public String generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, refreshTokenSecret, refreshTokenExpirationInMs);
    }

    private String generateToken(Authentication authentication, Key secret, int expirationInMs) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secret)
                .compact();
    }

    public Authentication getAuthenticationToken(String token, boolean isAccessToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(isAccessToken ? accessTokenSecret : refreshTokenSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        List<String> authorities = claims.get("authorities", List.class);

        Collection<? extends GrantedAuthority> grantedAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
    }

    public void validateToken(String authToken, boolean isAccessToken) {
        Jwts.parserBuilder()
                .setSigningKey(isAccessToken ? accessTokenSecret : refreshTokenSecret)
                .build()
                .parseClaimsJws(authToken);
    }
}
