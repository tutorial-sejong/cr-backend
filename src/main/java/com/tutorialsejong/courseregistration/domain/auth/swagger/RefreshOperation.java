package com.tutorialsejong.courseregistration.domain.auth.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "토큰 재발급", description = "Refresh Token 유효할 시, Access Token 재발급")
@Parameter(name="refreshToken", description = "발급 받은 Refresh Token", in = ParameterIn.COOKIE, required = true)
@ApiResponse
public @interface RefreshOperation {
}