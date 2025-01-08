package com.tutorialsejong.courseregistration.domain.auth.controller;

import com.tutorialsejong.courseregistration.domain.auth.dto.AuthenticationResult;
import com.tutorialsejong.courseregistration.domain.auth.dto.CaptchaResult;
import com.tutorialsejong.courseregistration.domain.auth.dto.JwtTokens;
import com.tutorialsejong.courseregistration.domain.auth.dto.LoginRequest;
import com.tutorialsejong.courseregistration.domain.auth.dto.LoginResponse;
import com.tutorialsejong.courseregistration.domain.auth.dto.MacroResponse;
import com.tutorialsejong.courseregistration.domain.auth.service.AuthService;
import com.tutorialsejong.courseregistration.domain.auth.service.CaptchaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
    private static final String COOKIE_PATH = "/";

    private final AuthService authService;
    private final CaptchaService captchaService;

    @Value("${app.jwt.refreshTokenExpirationInMs}")
    private int refreshTokenExpirationInMs;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
        AuthenticationResult authResult = authService.loginOrSignup(loginRequest);
        ResponseCookie refreshTokenCookie = createRefreshTokenCookie(authResult.refreshToken());

        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        LoginResponse loginResponse = new LoginResponse(authResult.accessToken(), authResult.username());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh token is missing");
        }

        JwtTokens jwtTokens = authService.refreshAccessToken(refreshToken);
        Map<String, Object> body = new HashMap<>();
        body.put("accessToken", jwtTokens.accessToken());
        return ResponseEntity.ok().body(body);
    }

    @DeleteMapping("/withdrawal/{studentId}")
    public ResponseEntity<?> withdrawal(@PathVariable("studentId") String studentId) {

        authService.withdrawalUser(studentId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/macro")
    public ResponseEntity<?> getMacro() {
        CaptchaResult captchaData = captchaService.generateCaptcha();
        return ResponseEntity.ok(new MacroResponse(200, captchaData));
    }

    private ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(Duration.ofMillis(refreshTokenExpirationInMs))
                .path(COOKIE_PATH)
                .build();
    }
}
