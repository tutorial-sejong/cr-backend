package com.tutorialsejong.courseregistration.domain.user.repository;

import com.tutorialsejong.courseregistration.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByStudentId(String studentId);

    @Modifying
    @Query("DELETE FROM User u WHERE u.studentId = :studentId")
    void deleteByStudentId(String studentId);
}
