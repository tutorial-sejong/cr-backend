package com.tutorialsejong.courseregistration.domain.schedule.swagger;

import com.tutorialsejong.courseregistration.domain.registration.dto.CourseRegistrationScheduleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.http.MediaType;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "인기 스케줄 조회", description = "위시카운트가 높은 스케줄을 조회합니다.")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "조회 성공",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = @ArraySchema(schema = @Schema(implementation = CourseRegistrationScheduleResponse.class))
                )
        )
})
@Parameter(
        name = "limit",
        description = "가져올 인기 스케줄 개수 (기본값=10)",
        in = ParameterIn.QUERY,
        required = false,
        example = "10"
)
public @interface GetPopularSchedulesOperation {
}