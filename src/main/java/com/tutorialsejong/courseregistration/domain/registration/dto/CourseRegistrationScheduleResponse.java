package com.tutorialsejong.courseregistration.domain.registration.dto;

import com.tutorialsejong.courseregistration.domain.schedule.entity.Schedule;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "수강 일정 응답 DTO")
public record CourseRegistrationScheduleResponse(
        @Schema(description = "수강 일정 ID", example = "1")
        Long scheduleId,

        @Schema(description = "학과 별칭", example = "대양휴머니티칼리지")
        String schDeptAlias,

        @Schema(description = "교과목 번호", example = "009352")
        String curiNo,

        @Schema(description = "분반 번호", example = "001")
        String classNo,

        @Schema(description = "단과 대학 별칭", example = "대양휴머니티칼리지")
        String schCollegeAlias,

        @Schema(description = "교과목 명", example = "사고와표현1")
        String curiNm,

        @Schema(description = "교과목 수업 언어", example = "영어")
        String curiLangNm,

        @Schema(description = "교과목 유형명", example = "공통교양필수")
        String curiTypeCdNm,

        @Schema(description = "선택영역 코드명", example = "학문기초")
        String sltDomainCdNm,

        @Schema(description = "시간 관련 정보", example = "3.0 / 3 / 0")
        String tmNum,

        @Schema(description = "학년", example = "1")
        String studentYear,

        @Schema(description = "이수 단위 그룹 코드명", example = "학사")
        String corsUnitGrpCdNm,

        @Schema(description = "운영 부서 명", example = "국어국문학과")
        String manageDeptNm,

        @Schema(description = "강사", example = "노지현")
        String lesnEmp,

        @Schema(description = "수업 시간", example = "화 목 09:00~10:30")
        String lesnTime,

        @Schema(description = "강의실", example = "세101")
        String lesnRoom,

        @Schema(description = "사이버 유형 코드명", example = "null", nullable = true)
        String cyberTypeCdNm,

        @Schema(description = "인턴십 유형 코드명", example = "null", nullable = true)
        String internshipTypeCdNm,

        @Schema(description = "교과목 편입/교환 여부", example = "null", nullable = true)
        String inoutSubCdtExchangeYn,

        @Schema(description = "비고", example = "외국인대상과목, 기초(Beginner)")
        String remark,

        @Schema(description = "희망 수 강 인원수", example = "1")
        Long wishCount
) {

    /**
     * 엔티티 {@link Schedule}로부터 DTO 객체를 생성합니다.
     */
    public static CourseRegistrationScheduleResponse from(Schedule schedule) {
        return new CourseRegistrationScheduleResponse(
                schedule.getScheduleId(),
                schedule.getSchDeptAlias(),
                schedule.getCuriNo(),
                schedule.getClassNo(),
                schedule.getSchCollegeAlias(),
                schedule.getCuriNm(),
                schedule.getCuriLangNm(),
                schedule.getCuriTypeCdNm(),
                schedule.getSltDomainCdNm(),
                schedule.getTmNum(),
                schedule.getStudentYear(),
                schedule.getCorsUnitGrpCdNm(),
                schedule.getManageDeptNm(),
                schedule.getLesnEmp(),
                schedule.getLesnTime(),
                schedule.getLesnRoom(),
                schedule.getCyberTypeCdNm(),
                schedule.getInternshipTypeCdNm(),
                schedule.getInoutSubCdtExchangeYn(),
                schedule.getRemark(),
                schedule.getWishCount()
        );
    }

    /**
     * 모든 필드를 인자로 받아 DTO 객체를 생성합니다.
     */
    public static CourseRegistrationScheduleResponse of(
            Long scheduleId,
            String schDeptAlias,
            String curiNo,
            String classNo,
            String schCollegeAlias,
            String curiNm,
            String curiLangNm,
            String curiTypeCdNm,
            String sltDomainCdNm,
            String tmNum,
            String studentYear,
            String corsUnitGrpCdNm,
            String manageDeptNm,
            String lesnEmp,
            String lesnTime,
            String lesnRoom,
            String cyberTypeCdNm,
            String internshipTypeCdNm,
            String inoutSubCdtExchangeYn,
            String remark,
            Long wishCount
    ) {
        return new CourseRegistrationScheduleResponse(
                scheduleId,
                schDeptAlias,
                curiNo,
                classNo,
                schCollegeAlias,
                curiNm,
                curiLangNm,
                curiTypeCdNm,
                sltDomainCdNm,
                tmNum,
                studentYear,
                corsUnitGrpCdNm,
                manageDeptNm,
                lesnEmp,
                lesnTime,
                lesnRoom,
                cyberTypeCdNm,
                internshipTypeCdNm,
                inoutSubCdtExchangeYn,
                remark,
                wishCount
        );
    }
}