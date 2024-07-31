package com.tutorialsejong.courseregistration.auth.service;

import com.tutorialsejong.courseregistration.auth.JwtTokenProvider;
import com.tutorialsejong.courseregistration.auth.dto.AuthenticationResult;
import com.tutorialsejong.courseregistration.auth.dto.JwtTokens;
import com.tutorialsejong.courseregistration.auth.dto.LoginRequest;
import com.tutorialsejong.courseregistration.user.entity.User;
import com.tutorialsejong.courseregistration.user.repository.InvalidRefreshTokenException;
import com.tutorialsejong.courseregistration.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        Authentication authentication = getAuthenticationFromRefreshToken(refreshToken);
        User user = getUserFromAuthentication(authentication);
        verifyRefreshTokenOwnership(user, refreshToken);
        return generateNewAccessToken(user, authentication);
    }

    private Authentication getAuthenticationFromRefreshToken(String refreshToken) {
        return tokenProvider.getAuthentication(refreshToken);
    }

    private User getUserFromAuthentication(Authentication authentication) {
        return userRepository.findByStudentId(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private void verifyRefreshTokenOwnership(User user, String refreshToken) {
        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }
    }

    private JwtTokens generateNewAccessToken(User user, Authentication authentication) {
        List<GrantedAuthority> authorities = authentication.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(user.getStudentId(), null, authorities);

        String newAccessToken = tokenProvider.generateAccessToken(newAuthentication);
        return new JwtTokens(newAccessToken, user.getRefreshToken());
    }
}
