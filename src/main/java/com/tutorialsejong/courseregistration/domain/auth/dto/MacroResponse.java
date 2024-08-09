package com.tutorialsejong.courseregistration.domain.auth.dto;

public class MacroResponse {
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
        private String answer;
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
