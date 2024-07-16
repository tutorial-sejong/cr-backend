package com.tutorialsejong.courseregistration.auth.controller;

import com.tutorialsejong.courseregistration.auth.dto.JwtResponseDTO;
import com.tutorialsejong.courseregistration.auth.dto.LoginRequestDTO;
import com.tutorialsejong.courseregistration.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        JwtResponseDTO jwtResponse = authService.loginOrSignup(loginRequest);

        Map<String, Object> body = new HashMap<>();
        body.put("accessToken", jwtResponse.accessToken());
        body.put("refreshToken", jwtResponse.refreshToken());

        return ResponseEntity.ok(body);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        JwtResponseDTO jwtResponse = authService.refreshToken(refreshToken);

        Map<String, Object> body = new HashMap<>();
        body.put("accessToken", jwtResponse.accessToken());

        return ResponseEntity.ok(body);
    }
}
