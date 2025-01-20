package com.tutorialsejong.courseregistration.domain.wishlist.swagger;

import com.tutorialsejong.courseregistration.domain.wishlist.dto.WishListRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "관심과목 삭제", description = "특정 학생의 관심 과목을 삭제한다.")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "삭제 성공",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(type = "string"),
                        examples = @ExampleObject(value = "관심과목이 삭제되었습니다.")
                )
        )
})
public @interface DeleteWishListOperation {
}