package com.tutorialsejong.courseregistration.desired.entity;

import java.io.Serializable;
import java.util.Objects;

public class DesiredCourseId implements Serializable {

    private String studentId;
    private String curiNo;
    private String classNo;

    public DesiredCourseId() {
        // 기본 생성자가 필요합니다.
    }

    public DesiredCourseId(String studentId, String curiNo, String classNo) {
        this.studentId = studentId;
        this.curiNo = curiNo;
        this.classNo = classNo;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCuriNo() {
        return curiNo;
    }

    public void setCuriNo(String curiNo) {
        this.curiNo = curiNo;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, curiNo, classNo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DesiredCourseId that = (DesiredCourseId) obj;
        return Objects.equals(studentId, that.studentId) &&
                Objects.equals(curiNo, that.curiNo) &&
                Objects.equals(classNo, that.classNo);
    }
}
