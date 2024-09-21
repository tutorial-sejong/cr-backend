package com.tutorialsejong.courseregistration.domain.wishlist.swagger;

import io.swagger.v3.oas.annotations.Operation;
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
@Operation(summary = "관심과목 저장", description = "관심과목 저장 API")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "성공",
                content = @Content(
                        mediaType = "text/plain",
                        schema = @Schema(type = "string", example = "관심과목이 저장되었습니다.")
                )
        )
})
public @interface SaveWishListOperation {
}
