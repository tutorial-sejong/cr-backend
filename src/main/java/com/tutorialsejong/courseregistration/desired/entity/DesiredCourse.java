package com.tutorialsejong.courseregistration.desired.entity;

import com.tutorialsejong.courseregistration.user.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "desired_course")
@IdClass(DesiredCourseId.class)
public class DesiredCourse {

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "studentId")
    private User studentId;

    @Id
    @Column(name = "curi_no")
    private String curiNo;

    @Id
    @Column(name = "class_no")
    private String classNo;

    @Column(name = "curi_nm")
    private String curiNm;

    public DesiredCourse(User studentId, String curiNo, String classNo, String curiNm) {
        this.studentId = studentId;
        this.curiNo = curiNo;
        this.classNo = classNo;
        this.curiNm = curiNm;
    }

    public DesiredCourse() {

    }

    public User getStudentId() {
        return studentId;
    }

    public String getCuriNo() {
        return curiNo;
    }

    public String getClassNo() {
        return classNo;
    }

    public String getCuriNm() {
        return curiNm;
    }
}