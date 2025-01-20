package com.tutorialsejong.courseregistration.domain.wishlist.controller;

import com.tutorialsejong.courseregistration.domain.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.domain.wishlist.dto.WishListRequest;
import com.tutorialsejong.courseregistration.domain.wishlist.service.WishListService;
import com.tutorialsejong.courseregistration.domain.wishlist.swagger.DeleteWishListOperation;
import com.tutorialsejong.courseregistration.domain.wishlist.swagger.GetWishListOperation;
import com.tutorialsejong.courseregistration.domain.wishlist.swagger.SaveWishListOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist")
@Tag(name = "관심과목 API", description = "관심과목 관련 API")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @SaveWishListOperation
    @PostMapping("/save")
    public ResponseEntity<?> saveWishListItem(@RequestBody WishListRequest wishListRequest) {
        wishListService.saveWishListItem(wishListRequest.studentId(), wishListRequest.scheduleId());
        return ResponseEntity.status(HttpStatus.CREATED).body("관심과목이 저장되었습니다.");
    }

    @GetWishListOperation
    @GetMapping
    public ResponseEntity<?> getWishList(@RequestParam String studentId) {
        List<Schedule> wishList = wishListService.getWishList(studentId);
        return ResponseEntity.ok(wishList);
    }

    @DeleteWishListOperation
    @DeleteMapping
    public ResponseEntity<?> deleteWishListItem(
            @Parameter(description = "11자리 이상 학번", example = "12345678911")
            @RequestParam String studentId,

            @Parameter(description = "강의 ID", example = "1")
            @RequestParam Long scheduleId
    ) {
        wishListService.deleteWishListItem(studentId, scheduleId);
        return ResponseEntity.ok("관심과목이 삭제되었습니다.");
    }
}
