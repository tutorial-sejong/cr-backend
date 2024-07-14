package com.tutorialsejong.courseregistration.schedule;

import com.tutorialsejong.courseregistration.schedule.dto.ScheduleSearchRequest;
import com.tutorialsejong.courseregistration.schedule.dto.ErrorDto;
import com.tutorialsejong.courseregistration.schedule.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    private static final Set<String> ALLOWED_PARAMS = Set.of(
            "schCollegeAlias", "schDeptAlias", "curiTypeCdNm", "sltDomainCdNm", "curiNm", "lesnEmp"
    );

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSearchSchedules(
            @RequestParam(required = false) String schCollegeAlias,
            @RequestParam(required = false) String schDeptAlias,
            @RequestParam(required = false) String curiTypeCdNm,
            @RequestParam(required = false) String sltDomainCdNm,
            @RequestParam(required = false) String curiNm,
            @RequestParam(required = false) String lesnEmp,
            WebRequest request
    ) {
        Set<String> invalidParams = StreamSupport.stream(
                        Spliterators.spliteratorUnknownSize(request.getParameterNames(), Spliterator.ORDERED), false)
                .filter(param -> !ALLOWED_PARAMS.contains(param))
                .collect(Collectors.toSet());

        if (!invalidParams.isEmpty()) {
            String message = "유효하지않은 Parameter. (" + String.join(", ", invalidParams) + ")";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDto(new Date(), message, request.getDescription(false)));
        }

        List<Schedule> searchResult = scheduleService.getSearchResultSchedules(new ScheduleSearchRequest(schCollegeAlias, schDeptAlias, curiTypeCdNm, sltDomainCdNm, curiNm, lesnEmp));

        if (searchResult.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(new Date(), "검색된 값 없음", request.getDescription(false)));
        }

        return ResponseEntity.ok().body(searchResult);
    }
}
