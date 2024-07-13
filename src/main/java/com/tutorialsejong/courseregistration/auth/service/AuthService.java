package com.tutorialsejong.courseregistration.auth.service;

import com.tutorialsejong.courseregistration.auth.JwtTokenProvider;
import com.tutorialsejong.courseregistration.auth.dto.JwtResponseDTO;
import com.tutorialsejong.courseregistration.auth.dto.LoginRequestDTO;
import com.tutorialsejong.courseregistration.user.entity.User;
import com.tutorialsejong.courseregistration.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        userRepository.findByStudentId(loginRequest.studentId()).orElseGet(() -> {
            User newUser = new User(loginRequest.studentId(), passwordEncoder.encode(loginRequest.password()));
            return userRepository.save(newUser);
        });

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.studentId(),
                        loginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return new JwtResponseDTO(jwt);
    }
}
