package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.schedule.data.Schedule;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public ScheduleDTO createSchedule( ScheduleDTO scheduleDTO) {

        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);

        //Insert a schedule
        Long id = scheduleRepository.createSchedule(schedule);

        //Find and return the schedule
        List<Schedule> scheduleList = scheduleRepository.findScheduleById(id);

        System.out.println("schedule values: "+ scheduleList.get(0));

        scheduleDTO = convertScheduleToScheduleDTO(scheduleList.get(0));
        System.out.println("scheduleDTO: " + scheduleDTO);

        return scheduleDTO;

    }

    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleRepository.findAllSchedules();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for(Schedule sc: scheduleList) {
            scheduleDTOS.add(convertScheduleToScheduleDTO(sc));
        }

        return scheduleDTOS;

    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        return scheduleDTO;
    }


    public List<ScheduleDTO> getScheduleForPet( long petId) {
        throw new UnsupportedOperationException();
    }


    public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
        throw new UnsupportedOperationException();
    }


    public List<ScheduleDTO> getScheduleForCustomer(long customerId) {
        throw new UnsupportedOperationException();
    }

}
