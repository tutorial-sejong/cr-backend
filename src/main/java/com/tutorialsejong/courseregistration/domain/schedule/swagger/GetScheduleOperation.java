package com.tutorialsejong.courseregistration.domain.schedule.swagger;

import com.tutorialsejong.courseregistration.domain.schedule.dto.ScheduleBadRequestResponse;
import com.tutorialsejong.courseregistration.domain.schedule.dto.ScheduleNotFoundResponse;
import com.tutorialsejong.courseregistration.domain.schedule.dto.ScheduleResponse;
import com.tutorialsejong.courseregistration.domain.schedule.dto.ScheduleSearchRequest;
import com.tutorialsejong.courseregistration.domain.schedule.exception.ScheduleNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "시간표 검색", description = "시간표 검색 API, 빈 값을 넣을 시 전체 시간표가 응답됩니다.")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "성공",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = @ArraySchema(schema = @Schema(implementation = ScheduleResponse.class))
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Bad Request",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ScheduleBadRequestResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Not Found",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ScheduleNotFoundResponse.class)
                )
        )
})
public @interface GetScheduleOperation {
}
