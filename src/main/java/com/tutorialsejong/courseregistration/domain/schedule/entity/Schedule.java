package com.tutorialsejong.courseregistration.domain.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_schedule")
@Getter
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(name = "sch_dept_alias")
    private String schDeptAlias;

    @Column(name = "curi_no")
    private String curiNo;

    @Column(name = "class_no")
    private String classNo;

    @Column(name = "sch_college_alias")
    private String schCollegeAlias;

    @Column(name = "curi_nm")
    private String curiNm;

    @Column(name = "curi_lang_nm")
    private String curiLangNm;

    @Column(name = "curi_type_cd_nm")
    private String curiTypeCdNm;

    @Column(name = "slt_domain_cd_nm")
    private String sltDomainCdNm;

    @Column(name = "tm_num")
    private String tmNum;

    @Column(name = "student_year")
    private String studentYear;

    @Column(name = "cors_unit_grp_cd_nm")
    private String corsUnitGrpCdNm;

    @Column(name = "manage_dept_nm")
    private String manageDeptNm;

    @Column(name = "lesn_emp")
    private String lesnEmp;

    @Column(name = "lesn_time")
    private String lesnTime;

    @Column(name = "lesn_room")
    private String lesnRoom;

    @Column(name = "cyber_type_cd_nm")
    private String cyberTypeCdNm;

    @Column(name = "internship_type_cd_nm")
    private String internshipTypeCdNm;

    @Column(name = "inout_sub_cdt_exchange_yn")
    private String inoutSubCdtExchangeYn;

    @Column(name = "remark")
    private String remark;

    @Column(name = "wish_count", nullable = false)
    private Long wishCount = 0L;

    public void incrementWishCount() {
        this.wishCount++;
    }

    public void decrementWishCount() {
        this.wishCount = Math.max(0, this.wishCount - 1);
    }
}
