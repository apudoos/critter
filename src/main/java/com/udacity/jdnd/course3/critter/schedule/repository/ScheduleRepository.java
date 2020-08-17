package com.udacity.jdnd.course3.critter.schedule.repository;

import com.udacity.jdnd.course3.critter.schedule.data.Schedule;

import java.util.List;

public interface ScheduleRepository {
    Long createSchedule(Schedule schedule);
    public List<Schedule> findAllSchedules();
    public List<Schedule> findScheduleById(Long id);
    //Schedule findScheduleById(Long Id);
    //List<Schedule> findAllSchedules();
    //List<ScheduleActivity> findAllScheduledActivity();
    //List<SchedulePets> findAllScheduledPets();
    //List<ScheduleEmployee> findAllScheduledEmployees();

}
