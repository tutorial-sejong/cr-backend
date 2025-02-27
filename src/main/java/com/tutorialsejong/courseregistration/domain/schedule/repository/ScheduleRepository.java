package com.tutorialsejong.courseregistration.domain.schedule.repository;

import com.tutorialsejong.courseregistration.domain.schedule.entity.Schedule;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE " +
            "(:curi_no IS NULL OR s.curiNo = :curi_no) AND " +
            "(:class_no IS NULL OR s.classNo = :class_no) AND " +
            "(:sch_college_alias IS NULL OR s.schCollegeAlias = :sch_college_alias) AND " +
            "(:sch_dept_alias IS NULL OR s.schDeptAlias = :sch_dept_alias) AND " +
            "(:curi_type_cd_nm IS NULL OR s.curiTypeCdNm = :curi_type_cd_nm) AND " +
            "(:slt_domain_cd_nm IS NULL OR s.sltDomainCdNm = :slt_domain_cd_nm) AND " +
            "(:curi_nm IS NULL OR " +
            "   CASE " +
            "       WHEN LENGTH(:curi_nm) >= 2 THEN s.curiNm LIKE %:curi_nm% " +
            "       ELSE s.curiNm = :curi_nm " +
            "   END) AND " +
            "(:lesn_emp IS NULL OR " +
            "   CASE " +
            "       WHEN LENGTH(:lesn_emp) >= 2 THEN s.lesnEmp LIKE %:lesn_emp% " +
            "       ELSE s.lesnEmp = :lesn_emp " +
            "   END)")
    List<Schedule> findAllBy(
            @Param("curi_no") String curiNo,
            @Param("class_no") String classNo,
            @Param("sch_college_alias") String schCollegeAlias,
            @Param("sch_dept_alias") String schDeptAlias,
            @Param("curi_type_cd_nm") String curiTypeCdNm,
            @Param("slt_domain_cd_nm") String sltDomainCdNm,
            @Param("curi_nm") String curiNm,
            @Param("lesn_emp") String lesnEmp
    );

    List<Schedule> findAllByOrderByWishCountDesc(Pageable pageable);
}
