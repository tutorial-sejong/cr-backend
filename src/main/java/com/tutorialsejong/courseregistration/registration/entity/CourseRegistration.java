package com.tutorialsejong.courseregistration.registration.entity;

import com.tutorialsejong.courseregistration.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_registration")
public class CourseRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "studentId")
    private User student;

    @ManyToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "scheduleId")
    private Schedule schedule;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    public CourseRegistration(User student, Schedule schedule, LocalDateTime registrationDate) {
        this.student = student;
        this.schedule = schedule;
        this.registrationDate = registrationDate;
    }

    public CourseRegistration() {

    }

    public Long getId() {
        return id;
    }

    public User getStudent() {
        return student;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
}
