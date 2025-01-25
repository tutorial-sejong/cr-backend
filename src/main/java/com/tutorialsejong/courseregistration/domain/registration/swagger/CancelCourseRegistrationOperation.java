package com.tutorialsejong.courseregistration.domain.registration.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
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
@Operation(summary = "단건 수강 취소", description = "해당 스케줄에 대해 등록된 수강을 취소한다.", security = @SecurityRequirement(name = "bearerAuth"))
@Parameter(name = "scheduleId", description = "취소할 수강의 스케줄 ID", required = true, in = ParameterIn.PATH, example = "1")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "수강 취소 성공",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
        )
})
public @interface CancelCourseRegistrationOperation {
}
