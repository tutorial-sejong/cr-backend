package com.tutorialsejong.courseregistration.domain.auth.service;

import com.tutorialsejong.courseregistration.common.security.JwtTokenProvider;
import com.tutorialsejong.courseregistration.domain.auth.dto.AuthenticationResult;
import com.tutorialsejong.courseregistration.domain.auth.dto.JwtTokens;
import com.tutorialsejong.courseregistration.domain.auth.dto.LoginRequest;
import com.tutorialsejong.courseregistration.domain.auth.dto.MacroResponse;
import com.tutorialsejong.courseregistration.domain.auth.exception.InvalidLoginException;
import com.tutorialsejong.courseregistration.domain.auth.exception.InvalidRefreshTokenException;
import com.tutorialsejong.courseregistration.domain.user.entity.User;
import com.tutorialsejong.courseregistration.domain.user.exception.UserNotFoundException;
import com.tutorialsejong.courseregistration.domain.user.repository.UserRepository;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
    private static final String COOKIE_PATH = "/";
    private static final List<Integer> MACRO_ANSWERS = Arrays.asList(
            1208, 2154, 2509, 2857, 3086,
            3458, 3511, 3803, 4613, 4139,
            5106, 5802, 5648, 6352, 7086,
            7414, 8415, 8594, 9468, 9102,
            1146, 1452, 2117, 3964, 4586,
            5148, 5549, 6180, 7597, 9383
    );

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.jwt.refreshTokenExpirationInMs}")
    private int refreshTokenExpirationInMs;

    @Transactional
    public AuthenticationResult loginOrSignup(LoginRequest loginRequest) {
        try {
            User user = findOrCreateUser(loginRequest);
            Authentication authentication = authenticate(loginRequest);
            JwtTokens jwtTokens = generateTokens(authentication, user);
            return new AuthenticationResult(jwtTokens.accessToken(), jwtTokens.refreshToken(), user.getStudentId());
        } catch (BadCredentialsException e) {
            throw new InvalidLoginException();
        }
    }

    @Transactional
    public JwtTokens refreshToken(String refreshToken) {
        if (refreshToken == null) {
            throw new InvalidRefreshTokenException();
        }

        String username = tokenProvider.getUsernameFromJWT(refreshToken);
        User user = userRepository.findByStudentId(username)
                .orElseThrow(UserNotFoundException::new);

        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }

        String newAccessToken = tokenProvider.generateAccessTokenFromUsername(username);
        return new JwtTokens(newAccessToken, refreshToken);
    }

    public MacroResponse generateMacroResponse() {
        Random random = new Random();
        int randomNumber = random.nextInt(30) + 1;

        MacroResponse.MacroData data = new MacroResponse.MacroData(
                MACRO_ANSWERS.get(randomNumber - 1).toString(),
                "/macro/" + randomNumber + ".jpg"
        );

        return new MacroResponse(200, data);
    }

    public ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(Duration.ofMillis(refreshTokenExpirationInMs))
                .path(COOKIE_PATH)
                .build();
    }

    private User findOrCreateUser(LoginRequest loginRequest) {
        return userRepository.findByStudentId(loginRequest.studentId())
                .orElseGet(() -> createNewUser(loginRequest));
    }

    private User createNewUser(LoginRequest loginRequest) {
        User newUser = new User(loginRequest.studentId(), encodePassword(loginRequest.password()));
        return userRepository.save(newUser);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private Authentication authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.studentId(),
                        loginRequest.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private JwtTokens generateTokens(Authentication authentication, User user) {
        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new JwtTokens(accessToken, refreshToken);
    }
}
