package com.tutorialsejong.courseregistration.domain.registration.service;

import com.tutorialsejong.courseregistration.domain.registration.dto.CourseRegistrationResponse;
import com.tutorialsejong.courseregistration.domain.registration.entity.CourseRegistration;
import com.tutorialsejong.courseregistration.domain.registration.exception.CourseAlreadyRegisteredException;
import com.tutorialsejong.courseregistration.domain.registration.repository.CourseRegistrationRepository;
import com.tutorialsejong.courseregistration.domain.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.domain.schedule.exception.ScheduleNotFoundException;
import com.tutorialsejong.courseregistration.domain.schedule.repository.ScheduleRepository;
import com.tutorialsejong.courseregistration.domain.user.entity.User;
import com.tutorialsejong.courseregistration.domain.user.exception.UserNotFoundException;
import com.tutorialsejong.courseregistration.domain.user.repository.UserRepository;
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
        User student = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new UserNotFoundException());
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException());

        boolean alreadyRegistered = courseRegistrationRepository.findAllByStudent(student)
                .stream()
                .anyMatch(registration -> registration.getSchedule().getCuriNo().equals(schedule.getCuriNo()));

        if (alreadyRegistered) {
            throw new CourseAlreadyRegisteredException();
        }

        try {
            CourseRegistration registration = new CourseRegistration(student, schedule, LocalDateTime.now());
            registration = courseRegistrationRepository.save(registration);
            return convertToDto(registration);
        } catch (DataIntegrityViolationException e) {
            throw new CourseAlreadyRegisteredException();
        }
    }

    @Transactional(readOnly = true)
    public List<Schedule> getRegisteredCourses(String studentId) {
        User student = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new UserNotFoundException());

        List<CourseRegistration> registrations = courseRegistrationRepository.findAllByStudent(student);

        return registrations.stream()
                .map(CourseRegistration::getSchedule)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelCourseRegistration(String studentId, Long scheduleId) {
        CourseRegistration registration = courseRegistrationRepository
                .findByStudentStudentIdAndScheduleScheduleId(studentId, scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException());

        courseRegistrationRepository.delete(registration);
    }

    @Transactional
    public void cancelAllCourseRegistrations(String studentId) {
        User student = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new UserNotFoundException());

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
