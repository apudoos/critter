package com.udacity.jdnd.course3.critter.schedule.data;

public class ScheduleEmployee {

    Long ScheduleId;
    Long EmployeeId;

    public ScheduleEmployee() {}

    public ScheduleEmployee(Long scheduleId, Long employeeId) {
        ScheduleId = scheduleId;
        EmployeeId = employeeId;
    }

    public Long getScheduleId() {
        return ScheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        ScheduleId = scheduleId;
    }

    public Long getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(Long employeeId) {
        EmployeeId = employeeId;
    }

    @Override
    public String toString() {
        return "ScheduleEmployee{" +
                "ScheduleId=" + ScheduleId +
                ", EmployeeId=" + EmployeeId +
                '}';
    }
}
