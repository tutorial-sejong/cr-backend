package com.tutorialsejong.courseregistration.domain.wishlist.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "관심과목 제거", description = "관심과목 제거 API")
@Parameters({
        @Parameter(name = "studentId", description = "11자리 이상 유저 학번", required = true, example = "12345678911", in = ParameterIn.QUERY),
        @Parameter(name = "scheduleId", description = "제거할 과목 번호", required = true, example = "1", in = ParameterIn.QUERY)
}

)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "성공",
                content = @Content(
                        mediaType = "text/plain",
                        schema = @Schema(type = "string", example = "관심과목이 삭제되었습니다.")
                )
        )
})
public @interface DeleteWishListOperation {
}
