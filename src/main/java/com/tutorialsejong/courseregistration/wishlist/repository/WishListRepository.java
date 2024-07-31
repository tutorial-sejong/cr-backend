package com.tutorialsejong.courseregistration.wishlist.repository;

import com.tutorialsejong.courseregistration.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.user.entity.User;
import com.tutorialsejong.courseregistration.wishlist.entity.WishList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, String> {

    List<WishList> findAllByStudentId(User studentId);

    Optional<WishList> findByStudentIdAndScheduleId(User user, Schedule schedule);

    boolean existsByStudentIdAndScheduleId(User studentId, Schedule scheduleId);

}
