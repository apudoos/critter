package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.schedule.data.Schedule;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule createSchedule( Schedule schedule) {
        //Insert a schedule
        Long id = scheduleRepository.createSchedule(schedule);

        //Find and return the schedule
        List<Schedule> scheduleList = scheduleRepository.findScheduleById(id);

        return scheduleList.get(0);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAllSchedules();
    }

    public List<Schedule> getScheduleForPet( Long petId) {
        return scheduleRepository.findScheduleByPetsId(petId);
    }

    public List<Schedule> getScheduleForEmployee( Long employeeId) {
        return scheduleRepository.findScheduleByEmployeeId(employeeId);
    }

    public List<Schedule> getScheduleForCustomer(long customerId) {
        return scheduleRepository.findScheduleForCustomerId(customerId);
    }

}
