package com.tutorialsejong.courseregistration.domain.registration.swagger;

import com.tutorialsejong.courseregistration.domain.registration.dto.CourseRegistrationScheduleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@Operation(summary = "등록 내역 조회", description = "로그인한 사용자의 수강 등록 내역을 조회", security = @SecurityRequirement(name = "bearerAuth"))
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "조회 성공",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = @ArraySchema(schema = @Schema(implementation = CourseRegistrationScheduleResponse.class))
                )
        ),
})
public @interface GetRegisteredCoursesOperation {
}
