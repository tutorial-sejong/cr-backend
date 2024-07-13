package com.tutorialsejong.courseregistration.schedule;

import com.tutorialsejong.courseregistration.schedule.dto.ScheduleSearchRequest;
import com.tutorialsejong.courseregistration.schedule.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/search")
    public List<Schedule> getSearchSchedules(
            @RequestParam(required = false) String curiTypeCdNm,
            @RequestParam(required = false) String sltDomainCdNm,
            @RequestParam(required = false) String curiNm,
            @RequestParam(required = false) String lesnEmp
    ) {
        ScheduleSearchRequest request = new ScheduleSearchRequest(curiTypeCdNm, sltDomainCdNm, curiNm, lesnEmp);
        return scheduleService.getSearchResultSchedules(request);
    }
}
