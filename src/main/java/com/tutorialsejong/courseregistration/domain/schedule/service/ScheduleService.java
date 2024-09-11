package com.tutorialsejong.courseregistration.domain.schedule.service;

import com.tutorialsejong.courseregistration.domain.registration.entity.CourseRegistration;
import com.tutorialsejong.courseregistration.domain.registration.repository.CourseRegistrationRepository;
import com.tutorialsejong.courseregistration.domain.schedule.dto.ScheduleSearchRequest;
import com.tutorialsejong.courseregistration.domain.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.domain.schedule.repository.ScheduleRepository;
import com.tutorialsejong.courseregistration.domain.user.entity.User;
import com.tutorialsejong.courseregistration.domain.user.exception.UserNotFoundException;
import com.tutorialsejong.courseregistration.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository,
                           CourseRegistrationRepository courseRegistrationRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    public List<Schedule> getSearchResultSchedules(ScheduleSearchRequest scheduleSearchRequest, String studentId) {
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new UserNotFoundException());

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

        List<Schedule> registeredSchedules = courseRegistrationRepository.findAllByStudent(user).stream()
                .map(CourseRegistration::getSchedule)
                .collect(Collectors.toList());

        return findAllByResult.stream()
                .filter(schedule -> !registeredSchedules.contains(schedule))
                .collect(Collectors.toList());
    }
}
