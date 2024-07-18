package com.tutorialsejong.courseregistration.schedule.repository;

import com.tutorialsejong.courseregistration.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {

    @Query("SELECT s FROM Schedule s WHERE " +
            "(:sch_college_alias IS NULL OR s.schCollegeAlias = :sch_college_alias) AND " +
            "(:sch_dept_alias IS NULL OR s.schDeptAlias = :sch_dept_alias) AND " +
            "(:curi_type_cd_nm IS NULL OR s.curiTypeCdNm = :curi_type_cd_nm) AND " +
            "(:slt_domain_cd_nm IS NULL OR s.sltDomainCdNm = :slt_domain_cd_nm) AND " +
            "(:curi_nm IS NULL OR s.curiNm = :curi_nm) AND " +
            "(:lesn_emp IS NULL OR s.lesnEmp = :lesn_emp)")
    List<Schedule> findAllBy(
            @Param("sch_college_alias") String schCollegeAlias,
            @Param("sch_dept_alias") String schDeptAlias,
            @Param("curi_type_cd_nm") String curiTypeCdNm,
            @Param("slt_domain_cd_nm") String sltDomainCdNm,
            @Param("curi_nm") String curiNm,
            @Param("lesn_emp") String lesnEmp
    );

    List<Schedule> findByCuriNoAndClassNo(String curiNo, String classNo);
}
