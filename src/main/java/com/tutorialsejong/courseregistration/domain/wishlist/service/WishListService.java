package com.tutorialsejong.courseregistration.domain.wishlist.service;

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
import org.springframework.stereotype.Service;

@Service
public class WishListService {

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
        User user = checkExistUser(studentId);
        Schedule schedule = checkExistSchedule(scheduleId);

        String curiNo = schedule.getCuriNo();

        boolean existsInWishList = wishListRepository.findAllByStudentId(user).stream()
                .anyMatch(wishList -> wishList.getScheduleId().getCuriNo().equals(curiNo));

        if (existsInWishList) {
            throw new AlreadyInWishlistException();
        }

        boolean existsInRegistration = courseRegistrationRepository
                .existsByStudentStudentIdAndScheduleScheduleId(studentId, scheduleId);

        if (existsInRegistration) {
            throw new WishlistCourseAlreadyRegisteredException();
        }

        WishList newWishList = new WishList(user, schedule);
        wishListRepository.save(newWishList);
    }

    public List<Schedule> getWishList(String studentId) {
        User user = checkExistUser(studentId);

        List<WishList> wishListList = wishListRepository.findAllByStudentId(user);

        return wishListList.stream()
                .map(WishList::getScheduleId)
                .filter(schedule -> !courseRegistrationRepository.existsByStudentStudentIdAndScheduleScheduleId(
                        studentId, schedule.getScheduleId())) // 수강신청된 과목 제외
                .collect(Collectors.toList());
    }

    public User checkExistUser(String studentId) {
        return userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new UserNotFoundException());
    }

    public Schedule checkExistSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException());
    }

    public void deleteWishListItem(String studentId, Long scheduleId) {
        User user = checkExistUser(studentId);
        Schedule schedule = checkExistSchedule(scheduleId);

        WishList wishList = wishListRepository.findByStudentIdAndScheduleId(user, schedule)
                .orElseThrow(() -> new WishlistNotFoundException());

        wishListRepository.delete(wishList);
    }

    public void deleteWishListsByStudent(String studentId) {
        wishListRepository.deleteByStudentId(studentId);
    }
}
