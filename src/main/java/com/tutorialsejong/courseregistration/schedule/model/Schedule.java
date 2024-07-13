package com.tutorialsejong.courseregistration.schedule.model;

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
    @Column(name = "class")
    private String class_;

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

    // Getters and setters

    public String getSchDeptAlias() {
        return schDeptAlias;
    }

    public void setSchDeptAlias(String schDeptAlias) {
        this.schDeptAlias = schDeptAlias;
    }

    public String getCuriNo() {
        return curiNo;
    }

    public void setCuriNo(String curiNo) {
        this.curiNo = curiNo;
    }

    public String getClass_() {
        return class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }

    public String getSchCollegeAlias() {
        return schCollegeAlias;
    }

    public void setSchCollegeAlias(String schCollegeAlias) {
        this.schCollegeAlias = schCollegeAlias;
    }

    public String getCuriNm() {
        return curiNm;
    }

    public void setCuriNm(String curiNm) {
        this.curiNm = curiNm;
    }

    public String getCuriLangNm() {
        return curiLangNm;
    }

    public void setCuriLangNm(String curiLangNm) {
        this.curiLangNm = curiLangNm;
    }

    public String getCuriTypeCdNm() {
        return curiTypeCdNm;
    }

    public void setCuriTypeCdNm(String curiTypeCdNm) {
        this.curiTypeCdNm = curiTypeCdNm;
    }

    public String getSltDomainCdNm() {
        return sltDomainCdNm;
    }

    public void setSltDomainCdNm(String sltDomainCdNm) {
        this.sltDomainCdNm = sltDomainCdNm;
    }

    public String getTmNum() {
        return tmNum;
    }

    public void setTmNum(String tmNum) {
        this.tmNum = tmNum;
    }

    public String getStudentYear() {
        return studentYear;
    }

    public void setStudentYear(String studentYear) {
        this.studentYear = studentYear;
    }

    public String getCorsUnitGrpCdNm() {
        return corsUnitGrpCdNm;
    }

    public void setCorsUnitGrpCdNm(String corsUnitGrpCdNm) {
        this.corsUnitGrpCdNm = corsUnitGrpCdNm;
    }

    public String getManageDeptNm() {
        return manageDeptNm;
    }

    public void setManageDeptNm(String manageDeptNm) {
        this.manageDeptNm = manageDeptNm;
    }

    public String getLesnEmp() {
        return lesnEmp;
    }

    public void setLesnEmp(String lesnEmp) {
        this.lesnEmp = lesnEmp;
    }

    public String getLesnTime() {
        return lesnTime;
    }

    public void setLesnTime(String lesnTime) {
        this.lesnTime = lesnTime;
    }

    public String getLesnRoom() {
        return lesnRoom;
    }

    public void setLesnRoom(String lesnRoom) {
        this.lesnRoom = lesnRoom;
    }

    public String getCyberTypeCdNm() {
        return cyberTypeCdNm;
    }

    public void setCyberTypeCdNm(String cyberTypeCdNm) {
        this.cyberTypeCdNm = cyberTypeCdNm;
    }

    public String getInternshipTypeCdNm() {
        return internshipTypeCdNm;
    }

    public void setInternshipTypeCdNm(String internshipTypeCdNm) {
        this.internshipTypeCdNm = internshipTypeCdNm;
    }

    public String getInoutSubCdtExchangeYn() {
        return inoutSubCdtExchangeYn;
    }

    public void setInoutSubCdtExchangeYn(String inoutSubCdtExchangeYn) {
        this.inoutSubCdtExchangeYn = inoutSubCdtExchangeYn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
