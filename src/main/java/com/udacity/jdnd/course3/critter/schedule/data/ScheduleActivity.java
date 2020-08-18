package com.udacity.jdnd.course3.critter.schedule.data;

//Contains activity which are scheduled for a day.
//Uses jdbcTemplate due to the complexity of data.
public class ScheduleActivity {
    Long scheduleId;
    String activity;

    public ScheduleActivity(Long scheduleId, String activity) {
        this.scheduleId = scheduleId;
        this.activity = activity;
    }

    public ScheduleActivity() {}

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
