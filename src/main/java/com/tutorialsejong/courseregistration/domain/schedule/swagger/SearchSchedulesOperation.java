package com.tutorialsejong.courseregistration.domain.schedule.swagger;

import com.tutorialsejong.courseregistration.domain.registration.dto.CourseRegistrationScheduleResponse;
import com.tutorialsejong.courseregistration.domain.schedule.dto.ErrorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "스케줄 검색",
        description = "검색 조건에 맞는 스케줄을 조회합니다."
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "검색 결과 성공",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = @ArraySchema(schema = @Schema(implementation = CourseRegistrationScheduleResponse.class))
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "유효하지 않은 파라미터",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorDto.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "검색된 값 없음",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorDto.class)
                )
        )
})
public @interface SearchSchedulesOperation {
}