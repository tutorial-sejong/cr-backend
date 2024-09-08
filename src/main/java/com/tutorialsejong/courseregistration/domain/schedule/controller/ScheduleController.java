package com.tutorialsejong.courseregistration.domain.schedule.controller;

import com.tutorialsejong.courseregistration.domain.schedule.dto.ErrorDto;
import com.tutorialsejong.courseregistration.domain.schedule.dto.ScheduleSearchRequest;
import com.tutorialsejong.courseregistration.domain.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.domain.schedule.service.ScheduleService;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private static final Set<String> ALLOWED_PARAMS = Set.of(
            "curiNo", "classNo", "schCollegeAlias", "schDeptAlias", "curiTypeCdNm", "sltDomainCdNm", "curiNm", "lesnEmp", "studentId"
    );

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSearchSchedules(ScheduleSearchRequest searchRequest,
                                                WebRequest request,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        Set<String> invalidParams = validateParameters(request);
        if (!invalidParams.isEmpty()) {
            String message = "유효하지않은 Parameter. (" + String.join(", ", invalidParams) + ")";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDto(new Date(), 400, message, request.getDescription(false)));
        }

        String studentId = userDetails.getUsername();
        List<Schedule> searchResult = scheduleService.getSearchResultSchedules(searchRequest, studentId);

        if (searchResult.isEmpty()) {
            return createErrorResponse(HttpStatus.NOT_FOUND, "검색된 값 없음", request);
        }

        return ResponseEntity.ok().body(searchResult);
    }

    private Set<String> validateParameters(WebRequest request) {
        return StreamSupport.stream(
                        Spliterators.spliteratorUnknownSize(request.getParameterNames(), Spliterator.ORDERED), false)
                .filter(param -> !ALLOWED_PARAMS.contains(param))
                .collect(Collectors.toSet());
    }

    private ResponseEntity<ErrorDto> createErrorResponse(HttpStatus status, String message, WebRequest request) {
        return ResponseEntity.status(status)
                .body(new ErrorDto(new Date(), status.value(), message, request.getDescription(false)));
    }
}
