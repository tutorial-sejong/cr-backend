package com.tutorialsejong.courseregistration.domain.registration.swagger;


import com.tutorialsejong.courseregistration.domain.schedule.dto.ScheduleResponse;
import com.tutorialsejong.courseregistration.domain.schedule.dto.UserNotFoundResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
@Operation(summary = "수강신청 불러오기", description = "유저의 수강신청 목록을 불러옵니다.")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "성공",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ScheduleResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "유저가 존재하지 않음",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = UserNotFoundResponse.class)
                )
        )
})
@SecurityRequirement(name="jwt")
public @interface GetRegisterCourseOperation {
}
