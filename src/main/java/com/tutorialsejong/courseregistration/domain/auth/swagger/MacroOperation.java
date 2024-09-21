package com.tutorialsejong.courseregistration.domain.auth.swagger;

import com.tutorialsejong.courseregistration.common.exception.ErrorResponse;
import com.tutorialsejong.courseregistration.common.security.AuthenticationFailedResponse;
import com.tutorialsejong.courseregistration.common.security.exception.AuthenticationFailedException;
import com.tutorialsejong.courseregistration.common.security.exception.SecurityErrorCode;
import com.tutorialsejong.courseregistration.domain.auth.dto.MacroResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
@Operation(summary = "매크로 코드", description = "매크로 코드 발급")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "성공",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = MacroResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "401",
                description = "인증 실패",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = AuthenticationFailedResponse.class)
                )
        )

})
@SecurityRequirement(name="jwt")
public @interface MacroOperation {
}