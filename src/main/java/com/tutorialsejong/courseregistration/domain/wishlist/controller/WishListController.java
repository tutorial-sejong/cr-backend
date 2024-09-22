package com.tutorialsejong.courseregistration.domain.wishlist.controller;

import com.tutorialsejong.courseregistration.domain.registration.dto.CourseRegistrationResponse;
import com.tutorialsejong.courseregistration.domain.wishlist.dto.WishListRequest;
import com.tutorialsejong.courseregistration.domain.wishlist.service.WishListService;
import com.tutorialsejong.courseregistration.domain.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.domain.wishlist.swagger.DeleteWishListOperation;
import com.tutorialsejong.courseregistration.domain.wishlist.swagger.GetWishListOperation;
import com.tutorialsejong.courseregistration.domain.wishlist.swagger.SaveWishListOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "관심과목", description = "관심과목 담기 API")
@RestController
@RequestMapping("/wishlist")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }


    @SaveWishListOperation
    @PostMapping("/save")
    public ResponseEntity<?> saveWishListItem(@RequestBody WishListRequest wishListRequest) {
        wishListService.saveWishListItem(wishListRequest.studentId(), wishListRequest.scheduleId());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetWishListOperation
    @GetMapping()
    public ResponseEntity<?> getWishList(
            @RequestParam("studentId") String studentId
    ) {
        List<Schedule> wishList = wishListService.getWishList(studentId);

        return ResponseEntity.status(HttpStatus.OK).body(wishList);
    }

    @DeleteWishListOperation
    @DeleteMapping
    public ResponseEntity<?> deleteWishListItem(
            @RequestParam("studentId") String studentId,

            @RequestParam("scheduleId") Long scheduleId) {
        wishListService.deleteWishListItem(studentId, scheduleId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
