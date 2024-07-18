package com.tutorialsejong.courseregistration.schedule.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "course_schedule")
@IdClass(ScheduleId.class)
public class Schedule {

    @Id
    @Column(name = "sch_dept_alias")
    private String schDeptAlias;

    @Id
    @Column(name = "curi_no")
    private String curiNo;

    @Id
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

    public String getSchDeptAlias() {
        return schDeptAlias;
    }

    public String getCuriNo() {
        return curiNo;
    }

    public String getClassNo() {
        return classNo;
    }

    public String getSchCollegeAlias() {
        return schCollegeAlias;
    }

    public String getCuriNm() {
        return curiNm;
    }

    public String getCuriLangNm() {
        return curiLangNm;
    }

    public String getCuriTypeCdNm() {
        return curiTypeCdNm;
    }

    public String getSltDomainCdNm() {
        return sltDomainCdNm;
    }

    public String getTmNum() {
        return tmNum;
    }

    public String getStudentYear() {
        return studentYear;
    }

    public String getCorsUnitGrpCdNm() {
        return corsUnitGrpCdNm;
    }

    public String getManageDeptNm() {
        return manageDeptNm;
    }

    public String getLesnEmp() {
        return lesnEmp;
    }

    public String getLesnTime() {
        return lesnTime;
    }

    public String getLesnRoom() {
        return lesnRoom;
    }

    public String getCyberTypeCdNm() {
        return cyberTypeCdNm;
    }

    public String getInternshipTypeCdNm() {
        return internshipTypeCdNm;
    }

    public String getInoutSubCdtExchangeYn() {
        return inoutSubCdtExchangeYn;
    }

    public String getRemark() {
        return remark;
    }
}
