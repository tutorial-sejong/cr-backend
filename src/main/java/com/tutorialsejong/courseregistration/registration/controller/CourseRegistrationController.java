package com.tutorialsejong.courseregistration.registration.controller;

import com.tutorialsejong.courseregistration.registration.entity.CourseRegistration;
import com.tutorialsejong.courseregistration.registration.service.CourseRegistrationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course-registration")
public class CourseRegistrationController {
    private final CourseRegistrationService courseRegistrationService;

    @Autowired
    public CourseRegistrationController(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

    @PostMapping("/register/{scheduleId}")
    public ResponseEntity<?> registerCourse(@PathVariable Long scheduleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String studentId = authentication.getName();
        CourseRegistration registration = courseRegistrationService.registerCourse(studentId, scheduleId);
        return ResponseEntity.status(HttpStatus.CREATED).body(registration);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getRegisteredCourses() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String studentId = authentication.getName();
        List<Long> registrations = courseRegistrationService.getRegisteredCourses(studentId);
        if (registrations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(registrations);
    }

    @DeleteMapping("/cancel/{scheduleId}")
    public ResponseEntity<?> cancelCourseRegistration(@PathVariable Long scheduleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String studentId = authentication.getName();

        courseRegistrationService.cancelCourseRegistration(studentId, scheduleId);
        return ResponseEntity.ok().build();
    }
}
