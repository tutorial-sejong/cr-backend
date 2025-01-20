package com.tutorialsejong.courseregistration.domain.schedule.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "스케줄 검색 요청 DTO")
public record ScheduleSearchRequest(
        @Schema(description = "교과목 번호", example = "009352")
        String curiNo,

        @Schema(description = "분반 번호", example = "001")
        String classNo,

        @Schema(description = "단과대학 별칭", example = "대양휴머니티칼리지")
        String schCollegeAlias,

        @Schema(description = "학과 별칭", example = "대양휴머니티칼리지")
        String schDeptAlias,

        @Schema(description = "교과목 유형명", example = "공통교양필수")
        String curiTypeCdNm,

        @Schema(description = "선택영역 코드명", example = "학문기초")
        String sltDomainCdNm,

        @Schema(description = "교과목 명", example = "사고와표현1")
        String curiNm,

        @Schema(description = "강사명", example = "노지현")
        String lesnEmp
) {

    public static ScheduleSearchRequest of(
            String curiNo,
            String classNo,
            String schCollegeAlias,
            String schDeptAlias,
            String curiTypeCdNm,
            String sltDomainCdNm,
            String curiNm,
            String lesnEmp
    ) {
        return new ScheduleSearchRequest(
                curiNo,
                classNo,
                schCollegeAlias,
                schDeptAlias,
                curiTypeCdNm,
                sltDomainCdNm,
                curiNm,
                lesnEmp
        );
    }
}
