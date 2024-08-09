package com.tutorialsejong.courseregistration.wishlist.controller;

import com.tutorialsejong.courseregistration.wishlist.dto.WishListRequest;
import com.tutorialsejong.courseregistration.wishlist.service.WishListService;
import com.tutorialsejong.courseregistration.schedule.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    private final WishListService wishListService;

    @Autowired
    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }


    @PostMapping("/save")
    public ResponseEntity<?> saveWishListItem(@RequestBody WishListRequest wishListRequest) {
        wishListService.saveWishListItem(wishListRequest.studentId(), wishListRequest.scheduleId());

        return ResponseEntity.status(HttpStatus.CREATED).body("관심과목이 저장되었습니다.");
    }

    @GetMapping()
    public ResponseEntity<?> getWishList(@RequestParam String studentId) {
        List<Schedule> wishList = wishListService.getWishList(studentId);

        return ResponseEntity.status(HttpStatus.OK).body(wishList);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteWishListItem(@RequestParam String studentId, @RequestParam Long scheduleId) {
        wishListService.deleteWishListItem(studentId, scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body("관심과목이 삭제되었습니다.");
    }
}
