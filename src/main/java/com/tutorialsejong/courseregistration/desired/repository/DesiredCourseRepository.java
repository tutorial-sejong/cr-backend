package com.tutorialsejong.courseregistration.desired.repository;

import com.tutorialsejong.courseregistration.desired.entity.DesiredCourse;
import com.tutorialsejong.courseregistration.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesiredCourseRepository extends JpaRepository<DesiredCourse, String> {

    List<DesiredCourse> findAllByStudentId(User studentId);
}
