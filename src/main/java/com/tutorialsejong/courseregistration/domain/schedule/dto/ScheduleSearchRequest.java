package com.tutorialsejong.courseregistration.domain.schedule.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ScheduleSearchRequest(
        @Schema(description = "학수번호", example = "009954")
        String curiNo,

        @Schema(description = "분반", example = "001")
        String classNo,

        @Schema(description = "개설대학", example = "소프트웨어융합대학")
        String schCollegeAlias,

        @Schema(description = "개설학과전공", example = "컴퓨터공학과")
        String schDeptAlias,

        @Schema(description = "이수구분", example = "전공필수")
        String curiTypeCdNm,

        @Schema(description = "선택영역", nullable = true)
        String sltDomainCdNm,

        @Schema(description = "교과목명", example = "알고리즘및실습")
        String curiNm,

        @Schema(description = "교수명", example = "신동규")
        String lesnEmp
) {
}
