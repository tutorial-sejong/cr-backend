package com.tutorialsejong.courseregistration.schedule;

import com.tutorialsejong.courseregistration.schedule.dto.ScheduleSearchRequest;
import com.tutorialsejong.courseregistration.schedule.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSearchResultSchedules(ScheduleSearchRequest scheduleSearchRequest) {
        List<Schedule> findAllByResult = scheduleRepository.findAllBy(
                scheduleSearchRequest.schCollegeAlias(),
                scheduleSearchRequest.schDeptAlias(),
                scheduleSearchRequest.curiTypeCdNm(),
                scheduleSearchRequest.sltDomainCdNm(),
                scheduleSearchRequest.curiNm(),
                scheduleSearchRequest.lesnEmp()
        );

        return findAllByResult.isEmpty() ? List.of() : findAllByResult;
    }
}
