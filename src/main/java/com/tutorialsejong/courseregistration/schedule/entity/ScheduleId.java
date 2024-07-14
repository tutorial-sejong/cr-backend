package com.tutorialsejong.courseregistration.schedule.entity;

import java.io.Serializable;
import java.util.Objects;

public class ScheduleId implements Serializable {
    private String schDeptAlias;
    private String curiNo;
    private String class_;

    public ScheduleId() {}

    public ScheduleId(String schDeptAlias, String curiNo, String class_) {
        this.schDeptAlias = schDeptAlias;
        this.curiNo = curiNo;
        this.class_ = class_;
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
        return class_;
    }

    public void setCLASS(String CLASS) {
        this.class_ = CLASS;
    }

    // equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleId that = (ScheduleId) o;
        return Objects.equals(schDeptAlias, that.schDeptAlias) &&
                Objects.equals(curiNo, that.curiNo) &&
                Objects.equals(class_, that.class_);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schDeptAlias, curiNo, class_);
    }
}
