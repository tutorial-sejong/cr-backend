package com.tutorialsejong.courseregistration.domain.auth.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "토큰 재발급", description = "Refresh Token 유효할 시, Access Token 재발급", security = @SecurityRequirement(name = "BearerAuth"))
@Parameter(name = "refreshToken", description = "발급 받은 Refresh Token", in = ParameterIn.COOKIE, required = true)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "성공",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Map.class),
                        examples = @ExampleObject(
                                value = """
                                        {
                                            "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"                                       
                                        }
                                        """
                        )
                )
        )
})
public @interface RefreshOperation {
}
