package com.tutorialsejong.courseregistration.schedule.entity;

import java.io.Serializable;
import java.util.Objects;

public class ScheduleId implements Serializable {
    private String schDeptAlias;
    private String curiNo;
    private String classNo;

    public ScheduleId() {}

    public ScheduleId(String schDeptAlias, String curiNo, String classNo) {
        this.schDeptAlias = schDeptAlias;
        this.curiNo = curiNo;
        this.classNo = classNo;
    }

    public String getschDeptAlias() {
        return schDeptAlias;
    }

    public void setschDeptAlias(String schDeptAlias) {
        this.schDeptAlias = schDeptAlias;
    }

    public String getcuriNo() {
        return curiNo;
    }

    public void setcuriNo(String curiNo) {
        this.curiNo = curiNo;
    }

    public String getCLASS() {
        return classNo;
    }

    public void setCLASS(String CLASS) {
        this.classNo = CLASS;
    }

    // equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleId that = (ScheduleId) o;
        return Objects.equals(schDeptAlias, that.schDeptAlias) &&
                Objects.equals(curiNo, that.curiNo) &&
                Objects.equals(classNo, that.classNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schDeptAlias, curiNo, classNo);
    }
}
