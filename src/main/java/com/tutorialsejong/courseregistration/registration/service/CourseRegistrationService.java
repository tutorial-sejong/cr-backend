package com.tutorialsejong.courseregistration.registration.service;

import com.tutorialsejong.courseregistration.exception.AlreadyRegisteredException;
import com.tutorialsejong.courseregistration.exception.CheckUserException;
import com.tutorialsejong.courseregistration.exception.CourseNotRegisteredException;
import com.tutorialsejong.courseregistration.registration.entity.CourseRegistration;
import com.tutorialsejong.courseregistration.registration.repository.CourseRegistrationRepository;
import com.tutorialsejong.courseregistration.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.schedule.repository.ScheduleRepository;
import com.tutorialsejong.courseregistration.user.entity.User;
import com.tutorialsejong.courseregistration.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CourseRegistrationService {
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public CourseRegistrationService(CourseRegistrationRepository courseRegistrationRepository,
                                     UserRepository userRepository,
                                     ScheduleRepository scheduleRepository) {
        this.courseRegistrationRepository = courseRegistrationRepository;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public CourseRegistration registerCourse(String studentId, Long scheduleId) {
        User student = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new CheckUserException("회원이 존재하지 않습니다. " + studentId));
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CheckUserException("강좌가 존재하지 않습니다. " + scheduleId));

        if (courseRegistrationRepository.existsByStudentStudentIdAndScheduleScheduleId(studentId, scheduleId)) {
            throw new AlreadyRegisteredException("이미 수강신청한 과목입니다.");
        }

        CourseRegistration registration = new CourseRegistration(student, schedule, LocalDateTime.now());
        return courseRegistrationRepository.save(registration);
    }

    public List<Long> getRegisteredCourses(String studentId) {
        return courseRegistrationRepository.findScheduleIdsByStudentId(studentId);
    }

    public void cancelCourseRegistration(String studentId, Long scheduleId) {
        CourseRegistration registration = courseRegistrationRepository
                .findByStudentStudentIdAndScheduleScheduleId(studentId, scheduleId)
                .orElseThrow(() -> new CourseNotRegisteredException("수강 신청하지 않은 과목입니다."));

        courseRegistrationRepository.delete(registration);
    }
}
