package com.tutorialsejong.courseregistration.domain.registration.swagger;


import com.tutorialsejong.courseregistration.common.exception.ErrorResponse;
import com.tutorialsejong.courseregistration.domain.registration.dto.CourseAlreadyRegisteredResponse;
import com.tutorialsejong.courseregistration.domain.registration.dto.CourseRegistrationResponse;
import com.tutorialsejong.courseregistration.domain.schedule.dto.ScheduleBadRequestResponse;
import com.tutorialsejong.courseregistration.domain.schedule.dto.ScheduleNotFoundResponse;
import com.tutorialsejong.courseregistration.domain.schedule.dto.ScheduleResponse;
import com.tutorialsejong.courseregistration.domain.schedule.dto.UserNotFoundResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@Operation(summary = "수강신청", description = "과목 ID를 통해 수강신청을 합니다.")
@Parameters({
        @Parameter(
                name = "scheduleId",
                description = "수깅신청 과목 번호",
                required = true,
                example = "1",
                in = ParameterIn.PATH
        )
})
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "201",
                description = "수강신청 성공",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = CourseRegistrationResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "유저가 존재하지 않음",
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
        @ApiResponse(
                responseCode = "409",
                description = "이미 신청된 과목",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = CourseAlreadyRegisteredResponse.class)
                )
        )
})
@SecurityRequirement(name="jwt")
public @interface RegisterCourseOperation {
}
