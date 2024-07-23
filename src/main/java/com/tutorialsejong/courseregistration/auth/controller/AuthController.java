package com.tutorialsejong.courseregistration.auth.controller;

import com.tutorialsejong.courseregistration.auth.dto.JwtResponseDTO;
import com.tutorialsejong.courseregistration.auth.dto.LoginRequestDTO;
import com.tutorialsejong.courseregistration.auth.dto.MacroResponse;
import com.tutorialsejong.courseregistration.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    private static final List<Integer> MACRO_ANSWERS = Arrays.asList(
            1208, 2154, 2509, 2857, 3086, 3458, 3511, 3803,
            4613, 4139, 5106, 5802, 5648, 6352, 7086, 7414,
            8415, 8594, 9468, 9102
    );

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
        JwtResponseDTO jwtResponse = authService.refreshAccessToken(refreshToken);

        Map<String, Object> body = new HashMap<>();
        body.put("accessToken", jwtResponse.accessToken());

        return ResponseEntity.ok(body);
    }

    @GetMapping("/macro")
    public ResponseEntity<?> verificationCodes() {
        Random random = new Random();
        int randomNumber = random.nextInt(20) + 1;

        MacroResponse.MacroData data = new MacroResponse.MacroData(MACRO_ANSWERS.get(randomNumber - 1).toString(), "/macro/" + randomNumber + ".jpg");
        MacroResponse body = new MacroResponse(200, data);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
