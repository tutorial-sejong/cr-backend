package com.tutorialsejong.courseregistration.domain.registration.swagger;

import com.tutorialsejong.courseregistration.domain.registration.dto.CourseRegistrationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.http.MediaType;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "수강 등록", description = "해당 스케줄에 수강 신청을 진행한다.", security = @SecurityRequirement(name = "bearerAuth"))
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "201",
                description = "수강 등록 성공",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = CourseRegistrationResponse.class)
                )
        )
})
public @interface RegisterCourseOperation {
}
