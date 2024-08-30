package com.tutorialsejong.courseregistration.auth.service;

import com.tutorialsejong.courseregistration.auth.JwtTokenProvider;
import com.tutorialsejong.courseregistration.auth.controller.AuthController;
import com.tutorialsejong.courseregistration.auth.dto.AuthenticationResult;
import com.tutorialsejong.courseregistration.auth.dto.JwtTokens;
import com.tutorialsejong.courseregistration.auth.dto.LoginRequest;
import com.tutorialsejong.courseregistration.registration.service.CourseRegistrationService;
import com.tutorialsejong.courseregistration.user.entity.User;
import com.tutorialsejong.courseregistration.user.repository.InvalidRefreshTokenException;
import com.tutorialsejong.courseregistration.user.repository.UserRepository;
import com.tutorialsejong.courseregistration.wishlist.service.WishListService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final WishListService wishListService;

    private final CourseRegistrationService courseRegistrationService;

    private final static Logger logger = LoggerFactory.getLogger(AuthService.class);


    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenProvider tokenProvider,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder, WishListService wishListService, CourseRegistrationService courseRegistrationService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        this.wishListService = wishListService;
        this.courseRegistrationService = courseRegistrationService;
    }

    public AuthenticationResult loginOrSignup(LoginRequest loginRequest) {
        User user = findOrCreateUser(loginRequest);
        Authentication authentication = authenticate(loginRequest);
        JwtTokens jwtTokens = generateTokens(authentication, user);

        logger.info("{} 로그인 성공", loginRequest);

        return new AuthenticationResult(jwtTokens.accessToken(), jwtTokens.refreshToken(), user.getStudentId());
    }

    private User findOrCreateUser(LoginRequest loginRequest) {
        return userRepository.findByStudentId(loginRequest.studentId())
                .orElseGet(() -> createNewUser(loginRequest));
    }

    private User createNewUser(LoginRequest loginRequest) {
        User newUser = new User(loginRequest.studentId(), encodePassword(loginRequest.password()));

        logger.info("{} 회원가입 성공", loginRequest.studentId());

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
                .orElseThrow(() -> {
                    logger.warn("{} DB에 존재하지 않아 refreshToken 발급 실패", username);
                    return new UsernameNotFoundException("User not found with username: " + username);
                });


        if (!user.getRefreshToken().equals(refreshToken)) {
            logger.warn("{} 잘못된 refreshToken으로 재발급 실패", username);
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }

        String newAccessToken = tokenProvider.generateAccessTokenFromUsername(username);
        logger.info("{} refreshToken 재발급 성공", username);
        return new JwtTokens(newAccessToken, refreshToken);
    }

    @Transactional
    public void withdrawalUser(String studentId) {

        wishListService.deleteWishListsByStudent(studentId);
        courseRegistrationService.deleteCourseRegistrationsByStudent(studentId);
        userRepository.deleteByStudentId(studentId);
    }
}
