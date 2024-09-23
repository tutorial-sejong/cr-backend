package com.tutorialsejong.courseregistration.domain.auth.controller;

import com.tutorialsejong.courseregistration.domain.auth.dto.AuthenticationResult;
import com.tutorialsejong.courseregistration.domain.auth.dto.JwtTokens;
import com.tutorialsejong.courseregistration.domain.auth.dto.LoginRequest;
import com.tutorialsejong.courseregistration.domain.auth.dto.LoginResponse;
import com.tutorialsejong.courseregistration.domain.auth.dto.MacroResponse;
import com.tutorialsejong.courseregistration.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest,
                                               HttpServletResponse response) {
        AuthenticationResult authResult = authService.loginOrSignup(loginRequest);
        setRefreshTokenCookie(response, authResult.refreshToken());
        return ResponseEntity.ok(new LoginResponse(authResult.accessToken(), authResult.username()));
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @CookieValue(name = REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken) {
        JwtTokens tokens = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/macro")
    public ResponseEntity<MacroResponse> verificationCodes() {
        MacroResponse macroResponse = authService.generateMacroResponse();
        return ResponseEntity.ok(macroResponse);
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        response.addHeader(HttpHeaders.SET_COOKIE, authService.createRefreshTokenCookie(refreshToken).toString());
    }
}
