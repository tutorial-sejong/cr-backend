package com.tutorialsejong.courseregistration.domain.schedule.dto;

import com.tutorialsejong.courseregistration.domain.schedule.entity.Schedule;

public record ScheduleResponse(
        Long scheduleId,
        String curiNm,          // 과목명
        String curiNo,          // 과목번호
        String classNo,         // 분반
        String manageDeptNm,    // 개설학과
        String lesnEmp,         // 담당교수
        String lesnTime,        // 강의시간
        String lesnRoom,        // 강의실
        Long wishCount,         // 관심과목 수
        Integer rank           // 순위
) {
    public static ScheduleResponse from(Schedule schedule, Integer rank) {
        return new ScheduleResponse(
                schedule.getScheduleId(),
                schedule.getCuriNm(),
                schedule.getCuriNo(),
                schedule.getClassNo(),
                schedule.getManageDeptNm(),
                schedule.getLesnEmp(),
                schedule.getLesnTime(),
                schedule.getLesnRoom(),
                schedule.getWishCount(),
                rank
        );
    }

    // rank 없는 버전의 from 메서드 - 일반 과목 조회시 사용
    public static ScheduleResponse from(Schedule schedule) {
        return from(schedule, null);
    }
}
