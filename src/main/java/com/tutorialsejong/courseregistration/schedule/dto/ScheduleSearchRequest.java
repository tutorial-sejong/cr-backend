package com.tutorialsejong.courseregistration.schedule.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ScheduleSearchRequest(
        String schCollegeAlias,
        String schDeptAlias,
        String curiTypeCdNm,
        String sltDomainCdNm,
        String curiNm,
        String lesnEmp
) {
}
