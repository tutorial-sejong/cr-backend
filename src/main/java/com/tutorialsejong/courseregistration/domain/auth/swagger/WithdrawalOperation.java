package com.tutorialsejong.courseregistration.domain.auth.swagger;

import com.tutorialsejong.courseregistration.domain.auth.dto.LoginResponse;
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
@Operation(summary = "회원 탈퇴", description = "회원 탈퇴 api")
@Parameters({
        @Parameter(
                name = "studentId",
                description = "탈퇴할 유저의 학번",
                required = true,
                example = "12345678911",
                in = ParameterIn.PATH
        )
})
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "성공",
                content = @Content()
        )
})

public @interface WithdrawalOperation {
}
