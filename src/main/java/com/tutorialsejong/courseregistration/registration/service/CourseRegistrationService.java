package com.tutorialsejong.courseregistration.registration.service;

import com.tutorialsejong.courseregistration.common.exception.AlreadyRegisteredException;
import com.tutorialsejong.courseregistration.common.exception.NotFoundException;
import com.tutorialsejong.courseregistration.registration.dto.CourseRegistrationResponse;
import com.tutorialsejong.courseregistration.registration.entity.CourseRegistration;
import com.tutorialsejong.courseregistration.registration.repository.CourseRegistrationRepository;
import com.tutorialsejong.courseregistration.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.schedule.repository.ScheduleRepository;
import com.tutorialsejong.courseregistration.user.entity.User;
import com.tutorialsejong.courseregistration.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public CourseRegistrationResponse registerCourse(String studentId, Long scheduleId) {
        User student = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + studentId));
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Schedule not found with id: " + scheduleId));

        if (courseRegistrationRepository.existsByStudentStudentIdAndScheduleScheduleId(studentId, scheduleId)) {
            throw new AlreadyRegisteredException("Course already registered");
        }

        CourseRegistration registration = new CourseRegistration(student, schedule, LocalDateTime.now());
        registration = courseRegistrationRepository.save(registration);
        return convertToDto(registration);
    }

    @Transactional(readOnly = true)
    public List<CourseRegistrationResponse> getRegisteredCourses(String studentId) {
        return courseRegistrationRepository.findCourseRegistrationResponsesByStudentId(studentId);
    }

    @Transactional
    public void cancelCourseRegistration(String studentId, Long scheduleId) {
        CourseRegistration registration = courseRegistrationRepository
                .findByStudentStudentIdAndScheduleScheduleId(studentId, scheduleId)
                .orElseThrow(() -> new NotFoundException("Course registration not found"));

        courseRegistrationRepository.delete(registration);
    }

    private CourseRegistrationResponse convertToDto(CourseRegistration registration) {
        return new CourseRegistrationResponse(
                registration.getStudent().getStudentId(),
                registration.getSchedule().getScheduleId()
        );
    }
}
