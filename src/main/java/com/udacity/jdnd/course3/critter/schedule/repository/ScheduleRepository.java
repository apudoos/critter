package com.udacity.jdnd.course3.critter.schedule.repository;

import com.udacity.jdnd.course3.critter.schedule.data.Schedule;

import java.util.List;

public interface ScheduleRepository {
    Long createSchedule(Schedule schedule);
    List<Schedule> findAllSchedules();
    List<Schedule> findScheduleById(Long id);
    List<Schedule> findScheduleByPetsId(Long id);
    List<Schedule> findScheduleByEmployeeId(Long id);
    List<Schedule> findScheduleForCustomerId(Long id);

}
