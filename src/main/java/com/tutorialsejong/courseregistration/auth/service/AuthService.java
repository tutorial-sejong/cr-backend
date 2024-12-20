package com.tutorialsejong.courseregistration.auth.service;

import com.tutorialsejong.courseregistration.auth.JwtTokenProvider;
import com.tutorialsejong.courseregistration.auth.dto.AuthenticationResult;
import com.tutorialsejong.courseregistration.auth.dto.JwtTokens;
import com.tutorialsejong.courseregistration.auth.dto.LoginRequest;
import com.tutorialsejong.courseregistration.user.entity.User;
import com.tutorialsejong.courseregistration.user.repository.InvalidRefreshTokenException;
import com.tutorialsejong.courseregistration.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenProvider tokenProvider,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResult loginOrSignup(LoginRequest loginRequest) {
        User user = findOrCreateUser(loginRequest);
        Authentication authentication = authenticate(loginRequest);
        JwtTokens jwtTokens = generateTokens(authentication, user);
        return new AuthenticationResult(jwtTokens.accessToken(), jwtTokens.refreshToken(), user.getStudentId());
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

    public JwtTokens refreshAccessToken(String refreshToken) {
        String username = tokenProvider.getUsernameFromJWT(refreshToken);

        User user = userRepository.findByStudentId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }

        String newAccessToken = tokenProvider.generateAccessTokenFromUsername(username);
        return new JwtTokens(newAccessToken, refreshToken);
    }
}
