package com.tutorialsejong.courseregistration.domain.registration.swagger;

import com.tutorialsejong.courseregistration.domain.schedule.dto.UserNotFoundResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "모든 수강신청 취소", description = "신청완료된 모든 과목을 취소합니다.")
@Parameters({
        @Parameter(
                name = "scheduleId",
                description = "수강신청 과목 번호",
                required = true,
                example = "1",
                in = ParameterIn.PATH
        )
})
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "모든 수강신청 취소 성공",
                content = @Content()
        ),
        @ApiResponse(
                responseCode = "404",
                description = "유저가 존재하지 않음",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = UserNotFoundResponse.class)
                )
        )
})
public @interface DeleteAllRegisterCourseOperation {
}
