package com.tutorialsejong.courseregistration.schedule.service;

import com.tutorialsejong.courseregistration.registration.entity.CourseRegistration;
import com.tutorialsejong.courseregistration.registration.repository.CourseRegistrationRepository;
import com.tutorialsejong.courseregistration.schedule.dto.ScheduleSearchRequest;
import com.tutorialsejong.courseregistration.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.schedule.repository.ScheduleRepository;
import com.tutorialsejong.courseregistration.user.entity.User;
import com.tutorialsejong.courseregistration.user.repository.UserRepository;
import com.tutorialsejong.courseregistration.wishlist.entity.WishList;
import com.tutorialsejong.courseregistration.wishlist.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, WishListRepository wishListRepository,
                           UserRepository userRepository, CourseRegistrationRepository courseRegistrationRepository) {
        this.scheduleRepository = scheduleRepository;
        this.wishListRepository = wishListRepository;
        this.userRepository = userRepository;
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    public List<Schedule> getSearchResultSchedules(ScheduleSearchRequest scheduleSearchRequest, String studentId) {
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        List<Schedule> findAllByResult = scheduleRepository.findAllBy(
                scheduleSearchRequest.curiNo(),
                scheduleSearchRequest.classNo(),
                scheduleSearchRequest.schCollegeAlias(),
                scheduleSearchRequest.schDeptAlias(),
                scheduleSearchRequest.curiTypeCdNm(),
                scheduleSearchRequest.sltDomainCdNm(),
                scheduleSearchRequest.curiNm(),
                scheduleSearchRequest.lesnEmp()
        );

        List<Schedule> wishListSchedules = wishListRepository.findAllByStudentId(user).stream()
                .map(WishList::getScheduleId)
                .collect(Collectors.toList());

        List<Schedule> registeredSchedules = courseRegistrationRepository.findAllByStudent(user).stream()
                .map(CourseRegistration::getSchedule)
                .collect(Collectors.toList());

        return findAllByResult.stream()
                .filter(schedule -> !wishListSchedules.contains(schedule))
                .collect(Collectors.toList());
    }
}
