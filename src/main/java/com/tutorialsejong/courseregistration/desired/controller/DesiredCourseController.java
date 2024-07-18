package com.tutorialsejong.courseregistration.desired.controller;

import com.tutorialsejong.courseregistration.desired.dto.DesiredCourseRequest;
import com.tutorialsejong.courseregistration.desired.entity.DesiredCourse;
import com.tutorialsejong.courseregistration.desired.service.DesiredCourseService;
import com.tutorialsejong.courseregistration.schedule.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/desired")
public class DesiredCourseController {

    private final DesiredCourseService desiredCourseService;

    @Autowired
    public DesiredCourseController(DesiredCourseService desiredCourseService) {
        this.desiredCourseService = desiredCourseService;
    }


    @PostMapping("/save")
    public ResponseEntity<?> saveDesiredCourse(@RequestBody DesiredCourseRequest desiredCourseRequest) {
        desiredCourseService.saveDesiredCourse(desiredCourseRequest.studentId(), desiredCourseRequest.desiredCourseList());

        return ResponseEntity.status(HttpStatus.CREATED).body("관심과목이 저장되었습니다.");
    }

}
