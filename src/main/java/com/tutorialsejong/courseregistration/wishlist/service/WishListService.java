package com.tutorialsejong.courseregistration.wishlist.service;

import com.tutorialsejong.courseregistration.exception.CheckUserException;
import com.tutorialsejong.courseregistration.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.schedule.repository.ScheduleRepository;
import com.tutorialsejong.courseregistration.user.entity.User;
import com.tutorialsejong.courseregistration.user.repository.UserRepository;
import com.tutorialsejong.courseregistration.wishlist.entity.WishList;
import com.tutorialsejong.courseregistration.wishlist.repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public WishListService(WishListRepository wishListRepository, UserRepository userRepository, ScheduleRepository scheduleRepository) {
        this.wishListRepository = wishListRepository;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public void saveWishList(String studentId, List<Long> wishListIdList) {
        User user = checkExistUser(studentId);

        List<WishList> wishList = wishListIdList.stream()
                .map(this::checkExistSchedule)
                .map(schedule -> new WishList(user, schedule))
                .collect(Collectors.toList());

        wishListRepository.saveAll(wishList);
    }

    public List<Schedule> getWishList(String studentId) {
        User user = checkExistUser(studentId);

        List<WishList> wishListList = wishListRepository.findAllByStudentId(user);

        return wishListList.stream()
                .map(WishList::getScheduleId)
                .collect(Collectors.toList());
    }

    public User checkExistUser(String studentId) {
        return userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new CheckUserException(studentId + "회원이 존재하지 않습니다."));
    }

    public Schedule checkExistSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CheckUserException(scheduleId + "과목이 존재하지않습니다."));
    }

    public void deleteWishList(String studentId, Long scheduleId) {
        User user = checkExistUser(studentId);
        Schedule schedule = checkExistSchedule(scheduleId);

        WishList wishList = wishListRepository.findByStudentIdAndScheduleId(user, schedule)
                .orElseThrow(() -> new CheckUserException("신청하지 않은 과목입니다."));

        wishListRepository.delete(wishList);
    }
}
