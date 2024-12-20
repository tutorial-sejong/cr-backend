package com.tutorialsejong.courseregistration.registration.repository;

import com.tutorialsejong.courseregistration.registration.dto.CourseRegistrationResponse;
import com.tutorialsejong.courseregistration.registration.entity.CourseRegistration;
import com.tutorialsejong.courseregistration.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {
    boolean existsByStudentStudentIdAndScheduleScheduleId(String studentId, Long scheduleId);

    Optional<CourseRegistration> findByStudentStudentIdAndScheduleScheduleId(String studentId, Long scheduleId);

    List<CourseRegistration> findAllByStudent(User student);

    @Query("SELECT new com.tutorialsejong.courseregistration.registration.dto.CourseRegistrationResponse(" +
            "cr.student.studentId, cr.schedule.scheduleId) " +
            "FROM CourseRegistration cr WHERE cr.student.studentId = :studentId")
    List<CourseRegistrationResponse> findCourseRegistrationResponsesByStudentId(@Param("studentId") String studentId);
}
