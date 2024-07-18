package com.tutorialsejong.courseregistration.desired.service;

import com.tutorialsejong.courseregistration.desired.dto.CourseInformation;
import com.tutorialsejong.courseregistration.desired.entity.DesiredCourse;
import com.tutorialsejong.courseregistration.desired.repository.DesiredCourseRepository;
import com.tutorialsejong.courseregistration.exception.CheckUserException;
import com.tutorialsejong.courseregistration.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.schedule.repository.ScheduleRepository;
import com.tutorialsejong.courseregistration.user.entity.User;
import com.tutorialsejong.courseregistration.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DesiredCourseService {

    private final DesiredCourseRepository desiredCourseRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public DesiredCourseService(DesiredCourseRepository desiredCourseRepository, UserRepository userRepository, ScheduleRepository scheduleRepository) {
        this.desiredCourseRepository = desiredCourseRepository;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public void saveDesiredCourse(String studentId, List<CourseInformation> desiredCourseList) {

        User user = checkExistUser(studentId);

        List<DesiredCourse> desiredCourses = desiredCourseList
                .stream()
                .map(request -> new DesiredCourse(user, request.curiNo(), request.classNo(), request.curiNm()))
                .collect(Collectors.toList());

        desiredCourseRepository.saveAll(desiredCourses);
    }

    public List<Schedule> getDesiredCourse(String studentId) {
        User user = checkExistUser(studentId);

        List<DesiredCourse> desiredCourseList = desiredCourseRepository.findAllByStudentId(user);

        return desiredCourseList.stream()
                .map(item -> scheduleRepository.findByCuriNoAndClassNo(item.getCuriNo(), item.getClassNo()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public User checkExistUser(String studentId) {
        return userRepository.findById(studentId)
                .orElseThrow(() -> new CheckUserException(studentId + "회원이 존재하지 않습니다."));
    }
}
