package com.tutorialsejong.courseregistration.domain.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ScheduleResponse(

        @Schema(description = "과목 번호", example = "1")
        Long scheduleId,

        @Schema(description = "학부/학과 별칭", example = "대양휴머니티칼리지")
        String schDeptAlias,

        @Schema(description = "교과목 번호", example = "003129")
        String curiNo,

        @Schema(description = "분반 번호", example = "001")
        String classNo,

        @Schema(description = "단과대학 별칭", example = "")
        String schCollegeAlias,

        @Schema(description = "교과목 이름", example = "지구과학개론")
        String curiNm,

        @Schema(description = "교과목 언어 이름", example = "")
        String curiLangNm,

        @Schema(description = "교과목 유형 코드 이름", example = "교선")
        String curiTypeCdNm,

        @Schema(description = "선택 영역 코드 이름", example = "자연과과학기술")
        String sltDomainCdNm,

        @Schema(description = "강의 시간 정보", example = "3.0 / 3 / 0")
        String tmNum,

        @Schema(description = "학년", example = "1")
        String studentYear,

        @Schema(description = "교육 과정 단위 그룹 코드 이름", example = "학사")
        String corsUnitGrpCdNm,

        @Schema(description = "관리 부서 이름", example = "지구자원시스템공학과")
        String manageDeptNm,

        @Schema(description = "강의 담당 교수", example = "정태웅")
        String lesnEmp,

        @Schema(description = "강의 시간", example = "월수13:30-15:00")
        String lesnTime,

        @Schema(description = "강의실", example = "군312")
        String lesnRoom,

        @Schema(description = "사이버 유형 코드 이름", example = "")
        String cyberTypeCdNm,

        @Schema(description = "인턴십 유형 코드 이름", example = "")
        String internshipTypeCdNm,

        @Schema(description = "내/외부 학점 교환 여부", example = "Y")
        String inoutSubCdtExchangeYn,

        @Schema(description = "비고", example = "")
        String remark

) {
}
