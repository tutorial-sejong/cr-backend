package com.tutorialsejong.courseregistration.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class MacroResponse {
    @Schema(description = "응답 코드", defaultValue = "200")
    private Integer statusCode;
    private MacroData data;

    public MacroResponse(Integer statusCode, MacroData data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public MacroData getData() {
        return data;
    }

    public void setData(MacroData data) {
        this.data = data;
    }

    public static class MacroData {
        @Schema(description = "정답", defaultValue = "1")
        private String answer;
        @Schema(description = "이미지 주소", defaultValue = "/macro/1.jpg")
        private String url;

        public MacroData(String answer, String url) {
            this.answer = answer;
            this.url = url;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
