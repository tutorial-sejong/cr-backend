package com.tutorialsejong.courseregistration.auth.controller;

import com.tutorialsejong.courseregistration.auth.dto.AuthenticationResult;
import com.tutorialsejong.courseregistration.auth.dto.JwtTokens;
import com.tutorialsejong.courseregistration.auth.dto.LoginRequest;
import com.tutorialsejong.courseregistration.auth.dto.LoginResponse;
import com.tutorialsejong.courseregistration.auth.dto.MacroResponse;
import com.tutorialsejong.courseregistration.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
    private static final String COOKIE_PATH = "/";
    private static final List<Integer> MACRO_ANSWERS = Arrays.asList(
            1208, 2154, 2509, 2857, 3086, 3458, 3511, 3803,
            4613, 4139, 5106, 5802, 5648, 6352, 7086, 7414,
            8415, 8594, 9468, 9102
    );

    private final AuthService authService;

    @Value("${app.jwt.refreshTokenExpirationInMs}")
    private int refreshTokenExpirationInMs;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is missing");
        }

        JwtTokens jwtTokens = authService.refreshAccessToken(refreshToken);
        Map<String, Object> body = new HashMap<>();
        body.put("accessToken", jwtTokens.accessToken());
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/macro")
    public ResponseEntity<?> verificationCodes() {
        MacroResponse body = createMacroResponse();
        return ResponseEntity.ok(body);
    }

    private ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("None")
                .maxAge(Duration.ofMillis(refreshTokenExpirationInMs))
                .path(COOKIE_PATH)
                .build();
    }

    private MacroResponse createMacroResponse() {
        Random random = new Random();
        int randomNumber = random.nextInt(20) + 1;

        MacroResponse.MacroData data = new MacroResponse.MacroData(
                MACRO_ANSWERS.get(randomNumber - 1).toString(),
                "/macro/" + randomNumber + ".jpg"
        );

        return new MacroResponse(200, data);
    }
}
