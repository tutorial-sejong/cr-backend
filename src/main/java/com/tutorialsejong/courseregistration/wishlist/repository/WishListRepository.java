package com.tutorialsejong.courseregistration.wishlist.repository;

import com.tutorialsejong.courseregistration.wishlist.entity.WishList;
import com.tutorialsejong.courseregistration.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, String> {

    List<WishList> findAllByStudentId(User studentId);
}
