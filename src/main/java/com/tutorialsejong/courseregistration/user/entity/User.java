package com.tutorialsejong.courseregistration.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String studentId;

    @Column(nullable = false)
    private String password;

    public User(final String studentId, final String password) {
        this.studentId = studentId;
        this.password = password;
    }

    public User() {

    }

    public String getStudentId() {
        return studentId;
    }

    public String getPassword() {
        return password;
    }
}
