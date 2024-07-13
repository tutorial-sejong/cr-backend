package com.tutorialsejong.courseregistration.schedule;

import com.tutorialsejong.courseregistration.schedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {

    @Query("SELECT s FROM Schedule s WHERE " +
            "(:curi_type_cd_nm IS NULL OR s.curiTypeCdNm = :curi_type_cd_nm) AND " +
            "(:slt_domain_cd_nm IS NULL OR s.sltDomainCdNm = :slt_domain_cd_nm) AND " +
            "(:curi_nm IS NULL OR s.curiNm = :curi_nm) AND " +
            "(:lesn_emp IS NULL OR s.lesnEmp = :lesn_emp)")
    List<Schedule> findAllBy(
            @Param("curi_type_cd_nm") String curiTypeCdNm,
            @Param("slt_domain_cd_nm") String sltDomainCdNm,
            @Param("curi_nm") String curiNm,
            @Param("lesn_emp") String lesnEmp
    );
}
