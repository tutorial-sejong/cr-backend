package com.tutorialsejong.courseregistration.registration.repository;

import com.tutorialsejong.courseregistration.registration.entity.CourseRegistration;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {
    boolean existsByStudentStudentIdAndScheduleScheduleId(String studentId, Long scheduleId);
    Optional<CourseRegistration> findByStudentStudentIdAndScheduleScheduleId(String studentId, Long scheduleId);
    @Query("SELECT cr.schedule.scheduleId FROM CourseRegistration cr WHERE cr.student.studentId = :studentId")
    List<Long> findScheduleIdsByStudentId(@Param("studentId") String studentId);
}
