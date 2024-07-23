package com.tutorialsejong.courseregistration.wishlist.service;

import com.tutorialsejong.courseregistration.wishlist.dto.CourseInformation;
import com.tutorialsejong.courseregistration.wishlist.entity.WishList;
import com.tutorialsejong.courseregistration.wishlist.repository.WishListRepository;
import com.tutorialsejong.courseregistration.exception.CheckUserException;
import com.tutorialsejong.courseregistration.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.schedule.repository.ScheduleRepository;
import com.tutorialsejong.courseregistration.user.entity.User;
import com.tutorialsejong.courseregistration.user.repository.UserRepository;
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

    public void saveWishList(String studentId, List<CourseInformation> wishListList) {

        User user = checkExistUser(studentId);

        List<WishList> wishList = wishListList
                .stream()
                .map(request -> new WishList(user, request.curiNo(), request.classNo(), request.curiNm()))
                .collect(Collectors.toList());

        wishListRepository.saveAll(wishList);
    }

    public List<Schedule> getWishList(String studentId) {
        User user = checkExistUser(studentId);

        List<WishList> wishListList = wishListRepository.findAllByStudentId(user);

        return wishListList.stream()
                .map(item -> scheduleRepository.findByCuriNoAndClassNo(item.getCuriNo(), item.getClassNo()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public User checkExistUser(String studentId) {
        return userRepository.findById(studentId)
                .orElseThrow(() -> new CheckUserException(studentId + "회원이 존재하지 않습니다."));
    }
}
