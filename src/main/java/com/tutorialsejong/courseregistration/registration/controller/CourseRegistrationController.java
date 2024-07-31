package com.tutorialsejong.courseregistration.registration.controller;


import com.tutorialsejong.courseregistration.registration.dto.CourseRegistrationResponse;
import com.tutorialsejong.courseregistration.registration.service.CourseRegistrationService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registrations")
public class CourseRegistrationController {

    private final CourseRegistrationService courseRegistrationService;

    public CourseRegistrationController(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

    @PostMapping("/{scheduleId}")
    public ResponseEntity<CourseRegistrationResponse> registerCourse(
            @PathVariable Long scheduleId,
            @AuthenticationPrincipal UserDetails userDetails) {
        CourseRegistrationResponse registration = courseRegistrationService.registerCourse(userDetails.getUsername(),
                scheduleId);
        return ResponseEntity.status(HttpStatus.CREATED).body(registration);
    }

    @GetMapping
    public ResponseEntity<List<CourseRegistrationResponse>> getRegisteredCourses(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<CourseRegistrationResponse> registrations = courseRegistrationService.getRegisteredCourses(
                userDetails.getUsername());
        if (registrations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(registrations);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> cancelCourseRegistration(
            @PathVariable Long scheduleId,
            @AuthenticationPrincipal UserDetails userDetails) {
        courseRegistrationService.cancelCourseRegistration(userDetails.getUsername(), scheduleId);
        return ResponseEntity.ok().build();
    }
}
