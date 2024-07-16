package com.tutorialsejong.courseregistration.auth.service;

import com.tutorialsejong.courseregistration.auth.JwtTokenProvider;
import com.tutorialsejong.courseregistration.auth.dto.JwtResponseDTO;
import com.tutorialsejong.courseregistration.auth.dto.LoginRequestDTO;
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

    public JwtResponseDTO loginOrSignup(LoginRequestDTO loginRequest) {
        User user = findOrCreateUser(loginRequest);
        Authentication authentication = authenticate(loginRequest);
        return generateTokens(authentication, user);
    }

    private User findOrCreateUser(LoginRequestDTO loginRequest) {
        return userRepository.findByStudentId(loginRequest.studentId())
                .orElseGet(() -> createNewUser(loginRequest));
    }

    private User createNewUser(LoginRequestDTO loginRequest) {
        User newUser = new User(loginRequest.studentId(), encodePassword(loginRequest.password()));
        return userRepository.save(newUser);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private Authentication authenticate(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.studentId(),
                        loginRequest.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private JwtResponseDTO generateTokens(Authentication authentication, User user) {
        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new JwtResponseDTO(accessToken, refreshToken);
    }

    public JwtResponseDTO refreshAccessToken(String refreshToken) {
        Authentication authentication = getAuthenticationFromRefreshToken(refreshToken);
        User user = getUserFromAuthentication(authentication);
        verifyRefreshTokenOwnership(user, refreshToken);
        return generateNewAccessToken(user, authentication);
    }

    private Authentication getAuthenticationFromRefreshToken(String refreshToken) {
        return tokenProvider.getAuthenticationToken(refreshToken, false);
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

    private JwtResponseDTO generateNewAccessToken(User user, Authentication authentication) {
        List<GrantedAuthority> authorities = authentication.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(user.getStudentId(), null, authorities);

        String newAccessToken = tokenProvider.generateAccessToken(newAuthentication);
        return new JwtResponseDTO(newAccessToken, user.getRefreshToken());
    }
}