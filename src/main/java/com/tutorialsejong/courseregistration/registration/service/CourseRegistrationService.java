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
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            User student = userRepository.findByStudentId(studentId)
                    .orElseThrow(() -> new NotFoundException("User not found with id: " + studentId));
            Schedule schedule = scheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new NotFoundException("Schedule not found with id: " + scheduleId));

            CourseRegistration registration = new CourseRegistration(student, schedule, LocalDateTime.now());
            registration = courseRegistrationRepository.save(registration);
            return convertToDto(registration);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyRegisteredException("Course already registered");
        }
    }

    @Transactional(readOnly = true)
    public List<Schedule> getRegisteredCourses(String studentId) {
        User student = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + studentId));

        List<CourseRegistration> registrations = courseRegistrationRepository.findAllByStudent(student);

        return registrations.stream()
                .map(CourseRegistration::getSchedule)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelCourseRegistration(String studentId, Long scheduleId) {
        CourseRegistration registration = courseRegistrationRepository
                .findByStudentStudentIdAndScheduleScheduleId(studentId, scheduleId)
                .orElseThrow(() -> new NotFoundException("Course registration not found"));

        courseRegistrationRepository.delete(registration);
    }

    @Transactional
    public void cancelAllCourseRegistrations(String studentId) {
        User student = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + studentId));

        List<CourseRegistration> registrations = courseRegistrationRepository.findAllByStudent(student);
        courseRegistrationRepository.deleteAll(registrations);
    }

    private CourseRegistrationResponse convertToDto(CourseRegistration registration) {
        return new CourseRegistrationResponse(
                registration.getStudent().getStudentId(),
                registration.getSchedule().getScheduleId()
        );
    }

    public void deleteCourseRegistrationsByStudent(String studentId) {
        courseRegistrationRepository.deleteByStudentId(studentId);
    }
}
