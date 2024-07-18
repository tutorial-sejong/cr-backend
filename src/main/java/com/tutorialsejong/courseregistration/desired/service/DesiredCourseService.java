package com.tutorialsejong.courseregistration.desired.service;

import com.tutorialsejong.courseregistration.desired.dto.CourseInformation;
import com.tutorialsejong.courseregistration.desired.entity.DesiredCourse;
import com.tutorialsejong.courseregistration.desired.repository.DesiredCourseRepository;
import com.tutorialsejong.courseregistration.exception.CheckUserException;
import com.tutorialsejong.courseregistration.user.entity.User;
import com.tutorialsejong.courseregistration.user.repository.UserRepository;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DesiredCourseService {

    private final DesiredCourseRepository desiredCourseRepository;
    private final UserRepository userRepository;

    public DesiredCourseService(DesiredCourseRepository desiredCourseRepository, UserRepository userRepository) {
        this.desiredCourseRepository = desiredCourseRepository;
        this.userRepository = userRepository;
    }

    public void saveDesiredCourse(String studentId, List<CourseInformation> desiredCourseList) {

        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new CheckUserException(studentId + "회원이 존재하지 않습니다."));

        List<DesiredCourse> desiredCourses = desiredCourseList
                .stream()
                .map(request -> new DesiredCourse(user, request.curiNo(), request.classNo(), request.curiNm()))
                .collect(Collectors.toList());

        desiredCourseRepository.saveAll(desiredCourses);
    }

}
