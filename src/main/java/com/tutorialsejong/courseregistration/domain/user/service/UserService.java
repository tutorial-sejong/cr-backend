package com.tutorialsejong.courseregistration.domain.user.service;

import com.tutorialsejong.courseregistration.domain.registration.service.CourseRegistrationService;
import com.tutorialsejong.courseregistration.domain.user.repository.UserRepository;
import com.tutorialsejong.courseregistration.domain.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final WishListService wishListService;
    private final CourseRegistrationService courseRegistrationService;

    @Transactional
    public void withdrawUser(String studentId) {
        wishListService.deleteWishListsByStudent(studentId);
        courseRegistrationService.deleteCourseRegistrationsByStudent(studentId);
        userRepository.deleteByStudentId(studentId);
//        invalidateUserTokens(username); 토큰 무효화 구현 필요
    }
}
