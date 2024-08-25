package com.tutorialsejong.courseregistration.domain.wishlist.entity;

import com.tutorialsejong.courseregistration.domain.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.domain.user.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "wish_list")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishListId;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "studentId")
    private User studentId;

    @ManyToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "scheduleId")
    private Schedule scheduleId;


    public WishList() {

    }

    public WishList(User studentId, Schedule scheduleId) {
        this.studentId = studentId;
        this.scheduleId = scheduleId;
    }

    public User getStudentId() {
        return studentId;
    }

    public void setStudentId(User studentId) {
        this.studentId = studentId;
    }

    public Schedule getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Schedule scheduleId) {
        this.scheduleId = scheduleId;
    }
}
