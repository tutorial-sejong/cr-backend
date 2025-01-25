package com.tutorialsejong.courseregistration.domain.auth.service;

import com.tutorialsejong.courseregistration.common.security.JwtTokenProvider;
import com.tutorialsejong.courseregistration.common.utils.log.LogAction;
import com.tutorialsejong.courseregistration.common.utils.log.LogMessage;
import com.tutorialsejong.courseregistration.common.utils.log.LogReason;
import com.tutorialsejong.courseregistration.common.utils.log.LogResult;
import com.tutorialsejong.courseregistration.domain.auth.dto.AuthenticationResult;
import com.tutorialsejong.courseregistration.domain.auth.dto.JwtTokens;
import com.tutorialsejong.courseregistration.domain.auth.dto.LoginRequest;
import com.tutorialsejong.courseregistration.domain.registration.service.CourseRegistrationService;
import com.tutorialsejong.courseregistration.domain.user.entity.User;
import com.tutorialsejong.courseregistration.domain.user.exception.UserNotFoundException;
import com.tutorialsejong.courseregistration.domain.user.repository.InvalidRefreshTokenException;
import com.tutorialsejong.courseregistration.domain.user.repository.UserRepository;
import com.tutorialsejong.courseregistration.domain.wishlist.service.WishListService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final WishListService wishListService;

    private final CourseRegistrationService courseRegistrationService;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenProvider tokenProvider,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder, WishListService wishListService,
                       CourseRegistrationService courseRegistrationService) {
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

        logger.info(LogMessage.builder()
                .action(LogAction.LOGIN)
                .subject("s"+user.getStudentId())
                .result(LogResult.SUCCESS)
                .build()
                .toString()
        );

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
                .orElseThrow(() -> new UserNotFoundException());

        if (!user.getRefreshToken().equals(refreshToken)) {
            logger.warn(LogMessage.builder()
                    .action(LogAction.REFRESH_TOKEN)
                    .subject("s"+username)
                    .result(LogResult.FAIL)
                    .reason(LogReason.INVALID_CREDENTIAL)
                    .build().toString());
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }

        String newAccessToken = tokenProvider.generateAccessTokenFromUsername(username);
        return new JwtTokens(newAccessToken, refreshToken);
    }

    @Transactional
    public void withdrawalUser(String studentId) {
        wishListService.deleteWishListsByStudent(studentId);
        courseRegistrationService.deleteCourseRegistrationsByStudent(studentId);
        userRepository.deleteByStudentId(studentId);

        logger.info(LogMessage.builder()
                .action(LogAction.WITHDRAWAL)
                .subject("s"+studentId)
                .result(LogResult.SUCCESS)
                .build()
                .toString());
    }
}
