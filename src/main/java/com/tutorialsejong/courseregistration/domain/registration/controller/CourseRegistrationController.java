package com.tutorialsejong.courseregistration.domain.registration.controller;

import com.tutorialsejong.courseregistration.domain.registration.dto.CourseRegistrationResponse;
import com.tutorialsejong.courseregistration.domain.registration.dto.CourseRegistrationScheduleResponse;
import com.tutorialsejong.courseregistration.domain.registration.service.CourseRegistrationService;
import com.tutorialsejong.courseregistration.domain.registration.swagger.CancelAllCourseRegistrationsOperation;
import com.tutorialsejong.courseregistration.domain.registration.swagger.CancelCourseRegistrationOperation;
import com.tutorialsejong.courseregistration.domain.registration.swagger.GetRegisteredCoursesOperation;
import com.tutorialsejong.courseregistration.domain.registration.swagger.RegisterCourseOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "수강신청 API", description = "수강신청 관련 API")
public class CourseRegistrationController {

    private final CourseRegistrationService courseRegistrationService;

    public CourseRegistrationController(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

    @RegisterCourseOperation
    @PostMapping("/{scheduleId}")
    public ResponseEntity<CourseRegistrationResponse> registerCourse(
            @Parameter(description = "강의 ID", required = true, example = "1")
            @PathVariable Long scheduleId,
            @AuthenticationPrincipal UserDetails userDetails) {
        CourseRegistrationResponse registration = courseRegistrationService.registerCourse(
                userDetails.getUsername(), scheduleId);
        return ResponseEntity.status(HttpStatus.CREATED).body(registration);
    }

    @GetRegisteredCoursesOperation
    @GetMapping
    public ResponseEntity<List<CourseRegistrationScheduleResponse>> getRegisteredCourses(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<CourseRegistrationScheduleResponse> registrations = courseRegistrationService.getRegisteredCourses(
                userDetails.getUsername());
        if (registrations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(registrations);
    }

    @CancelCourseRegistrationOperation
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> cancelCourseRegistration(
            @Parameter(description = "수강신청할 강의의 ID", required = true, example = "1")
            @PathVariable Long scheduleId,
            @AuthenticationPrincipal UserDetails userDetails) {
        courseRegistrationService.cancelCourseRegistration(userDetails.getUsername(), scheduleId);
        return ResponseEntity.ok().build();
    }

    @CancelAllCourseRegistrationsOperation
    @DeleteMapping("/all")
    public ResponseEntity<Void> cancelAllCourseRegistrations(
            @AuthenticationPrincipal UserDetails userDetails) {
        courseRegistrationService.cancelAllCourseRegistrations(userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
}
