package com.tutorialsejong.courseregistration.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CaptchaResult(
        @Schema(description = "캡차 정답(문자열)", example = "3427")
        String answer,

        @Schema(description = "캡차 이미지 주소 (Base64 인코딩 포함 Data URL 형태)",
                example = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAABQCAIAAADTD63nAAAA...")
        String url
) {
}
