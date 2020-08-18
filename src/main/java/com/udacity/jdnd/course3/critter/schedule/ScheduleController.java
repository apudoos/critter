package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.schedule.data.Schedule;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);
        schedule = scheduleService.createSchedule(schedule);
        return convertScheduleToScheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for(Schedule sc: scheduleList) {
            scheduleDTOS.add(convertScheduleToScheduleDTO(sc));
        }

        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> scheduleList = scheduleService.getScheduleForPet(petId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule sc: scheduleList) {
            scheduleDTOS.add(convertScheduleToScheduleDTO(sc));
        }
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> scheduleList = scheduleService.getScheduleForEmployee(employeeId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule sc: scheduleList) {
            scheduleDTOS.add(convertScheduleToScheduleDTO(sc));
        }
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> scheduleList = scheduleService.getScheduleForCustomer(customerId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule sc: scheduleList) {
            scheduleDTOS.add(convertScheduleToScheduleDTO(sc));
        }
        return scheduleDTOS;
    }

    //Convert DTO to Schedule data
    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }

    //Convert Schedule data to DTO
    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        return scheduleDTO;
    }
}
