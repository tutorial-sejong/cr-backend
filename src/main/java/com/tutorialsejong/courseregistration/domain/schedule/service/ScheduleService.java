package com.tutorialsejong.courseregistration.domain.schedule.service;

import com.tutorialsejong.courseregistration.domain.registration.entity.CourseRegistration;
import com.tutorialsejong.courseregistration.domain.registration.repository.CourseRegistrationRepository;
import com.tutorialsejong.courseregistration.domain.schedule.dto.ScheduleResponse;
import com.tutorialsejong.courseregistration.domain.schedule.dto.ScheduleSearchRequest;
import com.tutorialsejong.courseregistration.domain.schedule.entity.Schedule;
import com.tutorialsejong.courseregistration.domain.schedule.repository.ScheduleRepository;
import com.tutorialsejong.courseregistration.domain.user.entity.User;
import com.tutorialsejong.courseregistration.domain.user.exception.UserNotFoundException;
import com.tutorialsejong.courseregistration.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository,
                           CourseRegistrationRepository courseRegistrationRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    public List<Schedule> getSearchResultSchedules(ScheduleSearchRequest scheduleSearchRequest, String studentId) {
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(UserNotFoundException::new);

        List<Schedule> findAllByResult = scheduleRepository.findAllBy(
                scheduleSearchRequest.curiNo(),
                scheduleSearchRequest.classNo(),
                scheduleSearchRequest.schCollegeAlias(),
                scheduleSearchRequest.schDeptAlias(),
                scheduleSearchRequest.curiTypeCdNm(),
                scheduleSearchRequest.sltDomainCdNm(),
                scheduleSearchRequest.curiNm(),
                scheduleSearchRequest.lesnEmp()
        );

        List<Schedule> registeredSchedules = courseRegistrationRepository.findAllByStudent(user).stream()
                .map(CourseRegistration::getSchedule)
                .toList();

        return findAllByResult.stream()
                .filter(schedule -> !registeredSchedules.contains(schedule))
                .collect(Collectors.toList());
    }

    public List<ScheduleResponse> findPopularSchedules(int limit) {
        List<Schedule> schedules = scheduleRepository.findAllByOrderByWishCountDesc(PageRequest.of(0, limit));

        if (schedules.isEmpty()) {
            return Collections.emptyList();
        }

        return calculatePopularSchedulesWithRank(schedules);
    }

    private List<ScheduleResponse> calculatePopularSchedulesWithRank(List<Schedule> schedules) {
        List<ScheduleResponse> responses = new ArrayList<>(schedules.size());
        RankInfo rankInfo = new RankInfo();

        for (Schedule schedule : schedules) {
            rankInfo.updateRankIfWishCountChanged(schedule.getWishCount());
            responses.add(ScheduleResponse.from(schedule, rankInfo.getCurrentRank()));
        }

        return responses;
    }

    private static class RankInfo {
        @Getter
        private int currentRank = 1;
        private Long previousWishCount = null;

        public void updateRankIfWishCountChanged(Long newWishCount) {
            if (shouldIncrementRank(newWishCount)) {
                currentRank++;
            }
            previousWishCount = newWishCount;
        }

        private boolean shouldIncrementRank(Long newWishCount) {
            return previousWishCount != null && !newWishCount.equals(previousWishCount);
        }
    }
}
