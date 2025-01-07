package com.tutorialsejong.courseregistration.domain.registration.service;

import com.tutorialsejong.courseregistration.common.utils.log.LogAction;
import com.tutorialsejong.courseregistration.common.utils.log.LogMessage;
import com.tutorialsejong.courseregistration.common.utils.log.LogReason;
import com.tutorialsejong.courseregistration.common.utils.log.LogResult;
import com.tutorialsejong.courseregistration.domain.auth.controller.AuthController;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseRegistrationService {
    private static final Logger logger = LoggerFactory.getLogger(CourseRegistrationService.class);

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
        logger.info(LogMessage.builder()
                .action(LogAction.REGISTER_COURSE)
                .subject("s"+studentId)
                .objectName("c"+scheduleId)
                .result(LogResult.SUCCESS)
                .extras("Starting course registration")
                .build().toString());

        User student = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> {
                    logger.warn(LogMessage.builder()
                            .action(LogAction.VALIDATE_USER)
                            .subject("s"+studentId)
                            .result(LogResult.FAIL)
                            .reason(LogReason.NOT_FOUND)
                            .build().toString());
                    throw new UserNotFoundException();
                });

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> {
                    logger.warn(LogMessage.builder()
                            .action(LogAction.VALIDATE_COURSE)
                            .subject("s"+studentId)
                            .objectName("c"+scheduleId)
                            .result(LogResult.FAIL)
                            .reason(LogReason.NOT_FOUND)
                            .build().toString());
                    throw new ScheduleNotFoundException();
                });

        boolean alreadyRegistered = courseRegistrationRepository.findAllByStudent(student).stream()
                .anyMatch(registration -> registration.getSchedule().getCuriNo().equals(schedule.getCuriNo()));

        if (alreadyRegistered) {
            logger.warn(LogMessage.builder()
                    .action(LogAction.REGISTER_COURSE)
                    .subject("s"+studentId)
                    .objectName("c"+scheduleId)
                    .result(LogResult.FAIL)
                    .reason(LogReason.ALREADY_EXIST)
                    .build().toString());
            throw new CourseAlreadyRegisteredException();
        }

        try {
            CourseRegistration registration = new CourseRegistration(student, schedule, LocalDateTime.now());
            registration = courseRegistrationRepository.save(registration);
            logger.info(LogMessage.builder()
                    .action(LogAction.REGISTER_COURSE)
                    .subject("s"+studentId)
                    .objectName("c"+scheduleId)
                    .result(LogResult.SUCCESS)
                    .extras("Registration completed")
                    .build().toString());
            return convertToDto(registration);
        } catch (DataIntegrityViolationException e) {
            logger.error(LogMessage.builder()
                    .action(LogAction.REGISTER_COURSE)
                    .subject("s"+studentId)
                    .objectName("c"+scheduleId)
                    .result(LogResult.ERROR)
                    .reason(LogReason.ALREADY_EXIST)
                    .extras("Data integrity violation")
                    .build().toString());
            throw new CourseAlreadyRegisteredException();
        }
    }

    @Transactional(readOnly = true)
    public List<Schedule> getRegisteredCourses(String studentId) {
        logger.info(LogMessage.builder()
                .action(LogAction.FETCH_REGISTERED_COURSES)
                .subject("s"+studentId)
                .result(LogResult.SUCCESS)
                .extras("Fetching registered courses")
                .build().toString());

        User student = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> {
                    logger.warn(LogMessage.builder()
                            .action(LogAction.VALIDATE_USER)
                            .subject("s"+studentId)
                            .result(LogResult.FAIL)
                            .reason(LogReason.NOT_FOUND)
                            .build().toString());
                    throw new UserNotFoundException();
                });

        List<CourseRegistration> registrations = courseRegistrationRepository.findAllByStudent(student);
        return registrations.stream().map(CourseRegistration::getSchedule).collect(Collectors.toList());
    }

    @Transactional
    public void cancelCourseRegistration(String studentId, Long scheduleId) {
        logger.info(LogMessage.builder()
                .action(LogAction.WITHDRAWAL)
                .subject("s"+studentId)
                .objectName("c"+scheduleId)
                .result(LogResult.SUCCESS)
                .extras("Canceling course registration")
                .build().toString());

        CourseRegistration registration = courseRegistrationRepository
                .findByStudentStudentIdAndScheduleScheduleId(studentId, scheduleId)
                .orElseThrow(() -> {
                    logger.warn(LogMessage.builder()
                            .action(LogAction.VALIDATE_COURSE)
                            .subject("s"+studentId)
                            .objectName("c"+scheduleId)
                            .result(LogResult.FAIL)
                            .reason(LogReason.NOT_FOUND)
                            .build().toString());
                    throw new ScheduleNotFoundException();
                });

        courseRegistrationRepository.delete(registration);
        logger.info(LogMessage.builder()
                .action(LogAction.WITHDRAWAL)
                .subject("s"+studentId)
                .objectName("c"+scheduleId)
                .result(LogResult.SUCCESS)
                .extras("Successfully canceled")
                .build().toString());
    }

    @Transactional
    public void cancelAllCourseRegistrations(String studentId) {
        logger.info(LogMessage.builder()
                .action(LogAction.WITHDRAWAL)
                .subject("s"+studentId)
                .result(LogResult.SUCCESS)
                .extras("Starting cancellation of all registrations")
                .build().toString());

        User student = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> {
                    logger.warn(LogMessage.builder()
                            .action(LogAction.VALIDATE_USER)
                            .subject("s"+studentId)
                            .result(LogResult.FAIL)
                            .reason(LogReason.NOT_FOUND)
                            .build().toString());
                    throw new UserNotFoundException();
                });

        List<CourseRegistration> registrations = courseRegistrationRepository.findAllByStudent(student);
        courseRegistrationRepository.deleteAll(registrations);

        logger.info(LogMessage.builder()
                .action(LogAction.WITHDRAWAL)
                .subject("s"+studentId)
                .result(LogResult.SUCCESS)
                .extras("All registrations canceled successfully")
                .build().toString());
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
