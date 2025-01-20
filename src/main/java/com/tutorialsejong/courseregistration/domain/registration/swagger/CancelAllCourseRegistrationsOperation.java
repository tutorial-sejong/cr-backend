package com.tutorialsejong.courseregistration.domain.registration.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
@Operation(summary = "전체 수강 취소", description = "로그인한 사용자의 전체 수강 등록을 취소한다.", security = @SecurityRequirement(name = "bearerAuth"))
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "전체 수강 취소 성공",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
        )
})
public @interface CancelAllCourseRegistrationsOperation {
}