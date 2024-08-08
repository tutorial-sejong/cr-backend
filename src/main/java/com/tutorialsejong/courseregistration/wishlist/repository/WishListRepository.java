package com.tutorialsejong.courseregistration.wishlist.repository;

import com.tutorialsejong.courseregistration.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.user.entity.User;
import com.tutorialsejong.courseregistration.wishlist.entity.WishList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WishListRepository extends JpaRepository<WishList, String> {

    List<WishList> findAllByStudentId(User studentId);

    Optional<WishList> findByStudentIdAndScheduleId(User user, Schedule schedule);

    boolean existsByStudentIdAndScheduleId(User studentId, Schedule scheduleId);

    @Modifying
    @Query("DELETE FROM WishList w WHERE w.studentId.studentId = :studentId")
    void deleteByStudentId(String studentId);
}
