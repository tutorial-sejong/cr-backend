package com.tutorialsejong.courseregistration.schedule.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.UpperSnakeCaseStrategy.class)
public record ScheduleSearchRequest(
        String curiTypeCdNm,
        String sltDomainCdNm,
        String curiNm,
        String lesnEmp
) {
}
