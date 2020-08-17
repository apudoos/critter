package com.udacity.jdnd.course3.critter.schedule.data;

public class SchedulePets {

    Long ScheduleId;
    Long PetsId;

    public SchedulePets() {
    }

    public SchedulePets(Long scheduleId, Long petsId) {
        ScheduleId = scheduleId;
        PetsId = petsId;
    }

    public Long getScheduleId() {
        return ScheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        ScheduleId = scheduleId;
    }

    public Long getPetsId() {
        return PetsId;
    }

    public void setPetsId(Long petsId) {
        PetsId = petsId;
    }

    @Override
    public String toString() {
        return "SchedulePets{" +
                "ScheduleId=" + ScheduleId +
                ", PetsId=" + PetsId +
                '}';
    }
}
