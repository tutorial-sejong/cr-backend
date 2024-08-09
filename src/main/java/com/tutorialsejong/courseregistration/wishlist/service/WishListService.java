package com.tutorialsejong.courseregistration.wishlist.service;

import com.tutorialsejong.courseregistration.common.exception.AlreadyRegisteredException;
import com.tutorialsejong.courseregistration.common.exception.BadRequestException;
import com.tutorialsejong.courseregistration.common.exception.NotFoundException;
import com.tutorialsejong.courseregistration.registration.repository.CourseRegistrationRepository;
import com.tutorialsejong.courseregistration.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.schedule.repository.ScheduleRepository;
import com.tutorialsejong.courseregistration.user.entity.User;
import com.tutorialsejong.courseregistration.user.repository.UserRepository;
import com.tutorialsejong.courseregistration.wishlist.entity.WishList;
import com.tutorialsejong.courseregistration.wishlist.repository.WishListRepository;
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
            throw new AlreadyRegisteredException("이미 관심과목 담기된 과목입니다.");
        }

        boolean existsInRegistration = courseRegistrationRepository
                .existsByStudentStudentIdAndScheduleScheduleId(studentId, scheduleId);

        if (existsInRegistration) {
            throw new AlreadyRegisteredException("이미 수강신청된 과목입니다");
        }

        WishList newWishList = new WishList(user, schedule);
        wishListRepository.save(newWishList);
    }

    public List<Schedule> getWishList(String studentId) {
        User user = checkExistUser(studentId);

        List<WishList> wishListList = wishListRepository.findAllByStudentId(user);

        return wishListList.stream()
                .map(WishList::getScheduleId)
                .filter(schedule -> !courseRegistrationRepository.existsByStudentStudentIdAndScheduleScheduleId(studentId, schedule.getScheduleId())) // 수강신청된 과목 제외
                .collect(Collectors.toList());
    }

    public User checkExistUser(String studentId) {
        return userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new NotFoundException(studentId + "회원이 존재하지 않습니다."));
    }

    public Schedule checkExistSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException(scheduleId + "과목이 존재하지않습니다."));
    }

    public void deleteWishListItem(String studentId, Long scheduleId) {
        User user = checkExistUser(studentId);
        Schedule schedule = checkExistSchedule(scheduleId);

        WishList wishList = wishListRepository.findByStudentIdAndScheduleId(user, schedule)
                .orElseThrow(() -> new BadRequestException("신청하지 않은 과목입니다."));

        wishListRepository.delete(wishList);
    }

    public void deleteWishListsByStudent(String studentId) {
        wishListRepository.deleteByStudentId(studentId);
    }
}
