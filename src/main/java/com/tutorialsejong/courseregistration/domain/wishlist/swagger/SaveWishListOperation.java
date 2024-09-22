package com.tutorialsejong.courseregistration.domain.wishlist.swagger;

import com.tutorialsejong.courseregistration.common.exception.ErrorResponse;
import com.tutorialsejong.courseregistration.domain.schedule.dto.ScheduleNotFoundResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "관심과목 저장", description = "관심과목 저장 API")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "201",
                description = "성공",
                content = @Content()
        ),
        @ApiResponse(
                responseCode = "404",
                description = "유저 혹은 강의가 존재하지 않음",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = {
                                @ExampleObject(
                                        name = "USER_NOT_FOUND",
                                        summary = "유저가 존재하지 않음",
                                        value = "{ \"code\": \"R002\", \"message\": \"존재하지 않는 유저입니다.\" }"
                                ),
                                @ExampleObject(
                                        name = "SCHEDULE_NOT_FOUND",
                                        summary = "강의를 찾을 수 없음",
                                        value = "{ \"code\": \"S001\", \"message\": \"강의를 찾을 수 없습니다.\" }"
                                )
                        }
                )
        ),
})
@SecurityRequirement(name="jwt")
public @interface SaveWishListOperation {
}
