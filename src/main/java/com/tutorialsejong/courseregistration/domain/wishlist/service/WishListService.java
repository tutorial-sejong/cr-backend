package com.tutorialsejong.courseregistration.domain.wishlist.service;

import com.tutorialsejong.courseregistration.common.utils.log.LogAction;
import com.tutorialsejong.courseregistration.common.utils.log.LogMessage;
import com.tutorialsejong.courseregistration.common.utils.log.LogReason;
import com.tutorialsejong.courseregistration.common.utils.log.LogResult;
import com.tutorialsejong.courseregistration.domain.auth.controller.AuthController;
import com.tutorialsejong.courseregistration.domain.registration.repository.CourseRegistrationRepository;
import com.tutorialsejong.courseregistration.domain.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.domain.schedule.exception.ScheduleNotFoundException;
import com.tutorialsejong.courseregistration.domain.schedule.repository.ScheduleRepository;
import com.tutorialsejong.courseregistration.domain.user.entity.User;
import com.tutorialsejong.courseregistration.domain.user.exception.UserNotFoundException;
import com.tutorialsejong.courseregistration.domain.user.repository.UserRepository;
import com.tutorialsejong.courseregistration.domain.wishlist.entity.WishList;
import com.tutorialsejong.courseregistration.domain.wishlist.exception.AlreadyInWishlistException;
import com.tutorialsejong.courseregistration.domain.wishlist.exception.WishlistCourseAlreadyRegisteredException;
import com.tutorialsejong.courseregistration.domain.wishlist.exception.WishlistNotFoundException;
import com.tutorialsejong.courseregistration.domain.wishlist.repository.WishListRepository;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;

    public WishListService(WishListRepository wishListRepository,
                           UserRepository userRepository,
                           ScheduleRepository scheduleRepository,
                           CourseRegistrationRepository courseRegistrationRepository) {
        this.wishListRepository = wishListRepository;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    public void saveWishListItem(String studentId, Long scheduleId) {
        logger.info(LogMessage.builder()
                .action(LogAction.ADD_WISHLIST)
                .subject("s"+studentId)
                .objectName("c"+scheduleId)
                .result(LogResult.SUCCESS)
                .extras("Starting wishlist addition")
                .build().toString());

        User user = checkExistUser(studentId);
        Schedule schedule = checkExistSchedule(scheduleId);

        String curiNo = schedule.getCuriNo();

        boolean existsInWishList = wishListRepository.findAllByStudentId(user).stream()
                .anyMatch(wishList -> wishList.getScheduleId().getCuriNo().equals(curiNo));

        if (existsInWishList) {
            logger.warn(LogMessage.builder()
                    .action(LogAction.ADD_WISHLIST)
                    .subject("s"+studentId)
                    .objectName("c"+scheduleId)
                    .result(LogResult.FAIL)
                    .reason(LogReason.ALREADY_EXIST)
                    .build().toString());
            throw new AlreadyInWishlistException();
        }

        WishList newWishList = new WishList(user, schedule);
        wishListRepository.save(newWishList);
        logger.info(LogMessage.builder()
                .action(LogAction.ADD_WISHLIST)
                .subject("s"+studentId)
                .objectName("c"+scheduleId)
                .result(LogResult.SUCCESS)
                .extras("Successfully added to wishlist")
                .build().toString());
    }

    public List<Schedule> getWishList(String studentId) {
        logger.info(LogMessage.builder()
                .action(LogAction.FETCH_REGISTERED_COURSES)
                .subject("s"+studentId)
                .result(LogResult.SUCCESS)
                .extras("Fetching wishlist")
                .build().toString());

        User user = checkExistUser(studentId);

        List<WishList> wishListList = wishListRepository.findAllByStudentId(user);

        logger.info(LogMessage.builder()
                .action(LogAction.FETCH_REGISTERED_COURSES)
                .subject("s"+studentId)
                .result(LogResult.SUCCESS)
                .extras("Wishlist fetched successfully")
                .build().toString());

        return wishListList.stream()
                .map(WishList::getScheduleId)
                .filter(schedule -> !courseRegistrationRepository.existsByStudentStudentIdAndScheduleScheduleId(
                        studentId, schedule.getScheduleId())) // 수강신청된 과목 제외
                .collect(Collectors.toList());
    }

    public User checkExistUser(String studentId) {
        logger.info(LogMessage.builder()
                .action(LogAction.VALIDATE_USER)
                .subject("s"+studentId)
                .result(LogResult.SUCCESS)
                .extras("Validating user existence")
                .build().toString());

        return userRepository.findByStudentId(studentId)
                .orElseThrow(() -> {
                    logger.warn(LogMessage.builder()
                            .action(LogAction.VALIDATE_USER)
                            .subject("s"+studentId)
                            .result(LogResult.FAIL)
                            .reason(LogReason.NOT_FOUND)
                            .extras("User not found")
                            .build().toString());
                    return new UserNotFoundException();
                });
    }

    public Schedule checkExistSchedule(Long scheduleId) {
        logger.info(LogMessage.builder()
                .action(LogAction.VALIDATE_COURSE)
                .objectName("c"+scheduleId)
                .result(LogResult.SUCCESS)
                .extras("Validating schedule existence")
                .build().toString());

        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> {
                    logger.warn(LogMessage.builder()
                            .action(LogAction.VALIDATE_COURSE)
                            .objectName("c"+scheduleId)
                            .result(LogResult.FAIL)
                            .reason(LogReason.NOT_FOUND)
                            .extras("Schedule not found")
                            .build().toString());
                    return new ScheduleNotFoundException();
                });
    }

    public void deleteWishListItem(String studentId, Long scheduleId) {
        logger.info(LogMessage.builder()
                .action(LogAction.REMOVE_WISHLIST) // 추가된 LogAction
                .subject("s"+studentId)
                .objectName("c"+scheduleId)
                .result(LogResult.SUCCESS)
                .extras("Starting removal of wishlist item")
                .build().toString());

        User user = checkExistUser(studentId);
        Schedule schedule = checkExistSchedule(scheduleId);

        WishList wishList = wishListRepository.findByStudentIdAndScheduleId(user, schedule)
                .orElseThrow(() -> {
                    logger.warn(LogMessage.builder()
                            .action(LogAction.REMOVE_WISHLIST)
                            .subject("s"+studentId)
                            .objectName("c"+scheduleId)
                            .result(LogResult.FAIL)
                            .reason(LogReason.NOT_FOUND)
                            .extras("Wishlist item not found")
                            .build().toString());
                    return new WishlistNotFoundException();
                });

        wishListRepository.delete(wishList);
        logger.info(LogMessage.builder()
                .action(LogAction.REMOVE_WISHLIST)
                .subject("s"+studentId)
                .objectName("c"+scheduleId)
                .result(LogResult.SUCCESS)
                .extras("Removed wishlist item")
                .build().toString());
    }

    public void deleteWishListsByStudent(String studentId) {
        logger.info(LogMessage.builder()
                .action(LogAction.REMOVE_WISHLIST)
                .subject("s"+studentId)
                .result(LogResult.SUCCESS)
                .extras("Starting removal of all wishlist items for the user")
                .build().toString());

        wishListRepository.deleteByStudentId(studentId);

        logger.info(LogMessage.builder()
                .action(LogAction.REMOVE_WISHLIST)
                .subject("s"+studentId)
                .result(LogResult.SUCCESS)
                .extras("Removed all wishlist items for the user")
                .build().toString());
    }
}
