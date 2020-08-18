package com.udacity.jdnd.course3.critter.schedule.repository;

import com.udacity.jdnd.course3.critter.schedule.data.Schedule;
import com.udacity.jdnd.course3.critter.schedule.data.ScheduleActivity;
import com.udacity.jdnd.course3.critter.schedule.data.ScheduleEmployee;
import com.udacity.jdnd.course3.critter.schedule.data.SchedulePets;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional
public class ScheduleRepositoryImpl implements ScheduleRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SCHEDULE_ID = "scheduleId";
    private static final String EMPLOYEE_ID = "employeeId";
    private static final String PETS_ID = "petsId";
    private static final String OWNER_ID = "ownerId";
    private static final String ACTIVITY = "activity";
    private static final String DATE_VALUE = "date";

    private static final String INSERT_SCHEDULE = "insert into schedule (date) values (:date) " ;

    private static final String INSERT_EMPLOYEES =
            "INSERT INTO schedule_employee (schedule_id, employee_id) " +
                    "VALUES (:" + SCHEDULE_ID + ", :" + EMPLOYEE_ID + ")";

    private static final String INSERT_PETS =
            "INSERT INTO schedule_pets (schedule_id, pets_id) " +
                    "VALUES (:" + SCHEDULE_ID + ", :" + PETS_ID + ")";

    private static final String INSERT_ACTIVITY =
            "INSERT INTO schedule_activity (schedule_id, activity) " +
                    "VALUES (:" + SCHEDULE_ID + ", :" + ACTIVITY + ")";

    private static final String SELECT_SCHEDULE_BY_ID =
            " select s.id, se.employee_id, sp.pets_id, s.date, sa.activity from schedule s " +
            " left join schedule_employee se on s.id = se.schedule_id " +
            " left join schedule_pets sp on s.id = sp.schedule_id " +
            " left join schedule_activity sa on s.id = sa.schedule_id" +
            " where s.id = :" + SCHEDULE_ID;

    private static final String SELECT_ALL_SCHEDULE =
            " select s.id, se.employee_id, sp.pets_id, s.date, sa.activity from schedule s " +
                    " left join schedule_employee se on s.id = se.schedule_id " +
                    " left join schedule_pets sp on s.id = sp.schedule_id " +
                    " left join schedule_activity sa on s.id = sa.schedule_id";

    private static final String SELECT_SCHEDULE_BY_PETS_ID =
            " select s.id, se.employee_id, sp.pets_id, s.date, sa.activity from schedule s " +
                    " left join schedule_employee se on s.id = se.schedule_id " +
                    " left join schedule_pets sp on s.id = sp.schedule_id " +
                    " left join schedule_activity sa on s.id = sa.schedule_id" +
                    " where s.id in (select distinct schedule_id from schedule_pets where pets_id = :" + PETS_ID + ")";

    private static final String SELECT_SCHEDULE_BY_EMPLOYEE_ID =
            " select s.id, se.employee_id, sp.pets_id, s.date, sa.activity from schedule s " +
                    " left join schedule_employee se on s.id = se.schedule_id " +
                    " left join schedule_pets sp on s.id = sp.schedule_id " +
                    " left join schedule_activity sa on s.id = sa.schedule_id " +
                    " where s.id in (select distinct schedule_id from schedule_employee where employee_id  = :" + EMPLOYEE_ID + ")";

    private static final String SELECT_SCHEDULE_FOR_CUSTOMER_ID =
            " select s.id, se.employee_id, sp.pets_id, s.date, sa.activity from schedule s " +
                    " left join schedule_employee se on s.id = se.schedule_id " +
                    " left join schedule_pets sp on s.id = sp.schedule_id " +
                    " left join schedule_activity sa on s.id = sa.schedule_id" +
                    " where sp.pets_id in ( select distinct pet_id from pets where owner_id = :" + OWNER_ID + ")";

    private static final BeanPropertyRowMapper<Schedule> scheduleRowMapper =
            new BeanPropertyRowMapper<>(Schedule.class);
    private static final BeanPropertyRowMapper<ScheduleEmployee> scheduleEmpRowMapper =
            new BeanPropertyRowMapper<>(ScheduleEmployee.class);
    private static final BeanPropertyRowMapper<ScheduleActivity> scheduleActRowMapper =
            new BeanPropertyRowMapper<>(ScheduleActivity.class);
    private static final BeanPropertyRowMapper<SchedulePets> schedulePetsRowMapper =
            new BeanPropertyRowMapper<>(SchedulePets.class);

    @Override
    public Long createSchedule(Schedule schedule) {

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(schedule);

        KeyHolder key = new GeneratedKeyHolder();

        jdbcTemplate.update(INSERT_SCHEDULE,
                parameterSource,
                //new MapSqlParameterSource(DATE_VALUE, schedule.getDate())
                key);

        List<ScheduleActivity> sa = new ArrayList<>();
        for (EmployeeSkill a: schedule.getActivities()) {
            sa.add(new ScheduleActivity(key.getKey().longValue(), a.name()));
        }

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(sa.toArray());
        jdbcTemplate.batchUpdate(INSERT_ACTIVITY,batch);

        List<ScheduleEmployee> se = new ArrayList<>();
        for (Long a: schedule.getEmployeeIds()) {
            se.add(new ScheduleEmployee(key.getKey().longValue(), a));
        }

        batch = SqlParameterSourceUtils.createBatch(se.toArray());
        jdbcTemplate.batchUpdate(INSERT_EMPLOYEES, batch);

        List<SchedulePets> sp = new ArrayList<>();
        for (Long a: schedule.getPetIds()) {
            sp.add(new SchedulePets(key.getKey().longValue(), a));
        }

        batch = SqlParameterSourceUtils.createBatch(sp.toArray());
        jdbcTemplate.batchUpdate(INSERT_PETS, batch);

        return key.getKey().longValue();

    }

    @Override
    public List<Schedule> findAllSchedules() {
        return jdbcTemplate.query(SELECT_ALL_SCHEDULE,
                resultSet -> {
                    List<Schedule> scheduleData = new ArrayList<>();
                    Set<Long> petData = new HashSet<>();
                    Set<Long> empData = new HashSet<>();
                    Set<EmployeeSkill> empSkill = new HashSet<>();
                    Schedule tempSchedule = null;
                    int scheduleRow = 0;
                    int countRow = 0;
                    while (resultSet.next()) {
                        tempSchedule = scheduleRowMapper.mapRow(resultSet, scheduleRow);

                        if (countRow == 0) {
                            scheduleData.add(tempSchedule);

                        } else if (tempSchedule.getId() != scheduleData.get(scheduleRow).getId()) {
                            //System.out.println(tempCustomerData.getId() + " " + customerData.get(custRow).getId());
                            scheduleData.get(scheduleRow).setPetIds(petData.stream().collect(Collectors.toList()));
                            scheduleData.get(scheduleRow).setEmployeeIds(empData.stream().collect(Collectors.toList()));
                            scheduleData.get(scheduleRow).setActivities(empSkill.stream().collect(Collectors.toSet()));
                            petData.clear();
                            empData.clear();
                            empSkill.clear();
                            scheduleData.add(tempSchedule);
                            //tempCustomerData = null;
                            //System.out.println(customerData.get(custRow).toString());
                            scheduleRow++;
                        }
                        petData.add(schedulePetsRowMapper.mapRow(resultSet, countRow).getPetsId());
                        empData.add(scheduleEmpRowMapper.mapRow(resultSet, countRow).getEmployeeId());
                        empSkill.add(EmployeeSkill.valueOf(scheduleActRowMapper.mapRow(resultSet, countRow++).getActivity()));
                    }
                    if (tempSchedule !=null) {
                        scheduleData.get(scheduleRow).setPetIds(petData.stream().collect(Collectors.toList()));
                        scheduleData.get(scheduleRow).setEmployeeIds(empData.stream().collect(Collectors.toList()));
                        scheduleData.get(scheduleRow).setActivities(empSkill.stream().collect(Collectors.toSet()));
                    }
                    return scheduleData;
                });
    }

    @Override
    public List<Schedule> findScheduleById(Long id) {
        return jdbcTemplate.query(SELECT_SCHEDULE_BY_ID,
                new MapSqlParameterSource().addValue(SCHEDULE_ID, id),
                resultSet -> {
                    List<Schedule> scheduleData = new ArrayList<>();
                    Set<Long> petData = new HashSet<>();
                    Set<Long> empData = new HashSet<>();
                    Set<EmployeeSkill> empSkill = new HashSet<>();
                    Schedule tempSchedule = null;
                    int scheduleRow = 0;
                    int countRow = 0;
                    while (resultSet.next()) {
                        tempSchedule = scheduleRowMapper.mapRow(resultSet, scheduleRow);

                        if (countRow == 0) {
                            scheduleData.add(tempSchedule);

                        } else if (tempSchedule.getId() != scheduleData.get(scheduleRow).getId()) {
                            //System.out.println(tempCustomerData.getId() + " " + customerData.get(custRow).getId());
                            scheduleData.get(scheduleRow).setPetIds(petData.stream().collect(Collectors.toList()));
                            scheduleData.get(scheduleRow).setEmployeeIds(empData.stream().collect(Collectors.toList()));
                            scheduleData.get(scheduleRow).setActivities(empSkill.stream().collect(Collectors.toSet()));
                            petData.clear();
                            empData.clear();
                            empSkill.clear();
                            scheduleData.add(tempSchedule);

                            scheduleRow++;
                        }

                        petData.add(schedulePetsRowMapper.mapRow(resultSet, countRow).getPetsId());

                        empData.add(scheduleEmpRowMapper.mapRow(resultSet, countRow).getEmployeeId());
                        empSkill.add(EmployeeSkill.valueOf(scheduleActRowMapper.mapRow(resultSet, countRow++).getActivity()));
                    }
                    if (tempSchedule !=null) {
                        scheduleData.get(scheduleRow).setPetIds(petData.stream().collect(Collectors.toList()));
                        scheduleData.get(scheduleRow).setEmployeeIds(empData.stream().collect(Collectors.toList()));
                        scheduleData.get(scheduleRow).setActivities(empSkill.stream().collect(Collectors.toSet()));
                    }
                    return scheduleData;
                });
    }

    @Override
    public List<Schedule> findScheduleByPetsId(Long id) {
        return jdbcTemplate.query(SELECT_SCHEDULE_BY_PETS_ID,
                new MapSqlParameterSource().addValue(PETS_ID, id),
                resultSet -> {
                    List<Schedule> scheduleData = new ArrayList<>();
                    Set<Long> petData = new HashSet<>();
                    Set<Long> empData = new HashSet<>();
                    Set<EmployeeSkill> empSkill = new HashSet<>();
                    Schedule tempSchedule = null;
                    int scheduleRow = 0;
                    int countRow = 0;
                    while (resultSet.next()) {
                        tempSchedule = scheduleRowMapper.mapRow(resultSet, scheduleRow);

                        if (countRow == 0) {
                            scheduleData.add(tempSchedule);

                        } else if (tempSchedule.getId() != scheduleData.get(scheduleRow).getId()) {
                            //System.out.println(tempCustomerData.getId() + " " + customerData.get(custRow).getId());
                            scheduleData.get(scheduleRow).setPetIds(petData.stream().collect(Collectors.toList()));
                            scheduleData.get(scheduleRow).setEmployeeIds(empData.stream().collect(Collectors.toList()));
                            scheduleData.get(scheduleRow).setActivities(empSkill.stream().collect(Collectors.toSet()));
                            petData.clear();
                            empData.clear();
                            empSkill.clear();
                            scheduleData.add(tempSchedule);
                            //tempCustomerData = null;
                            //System.out.println(customerData.get(custRow).toString());
                            scheduleRow++;
                        }

                        petData.add(schedulePetsRowMapper.mapRow(resultSet, countRow).getPetsId());

                        empData.add(scheduleEmpRowMapper.mapRow(resultSet, countRow).getEmployeeId());
                        empSkill.add(EmployeeSkill.valueOf(scheduleActRowMapper.mapRow(resultSet, countRow++).getActivity()));
                    }
                    if (tempSchedule !=null) {
                        scheduleData.get(scheduleRow).setPetIds(petData.stream().collect(Collectors.toList()));
                        scheduleData.get(scheduleRow).setEmployeeIds(empData.stream().collect(Collectors.toList()));
                        scheduleData.get(scheduleRow).setActivities(empSkill.stream().collect(Collectors.toSet()));
                    }
                    return scheduleData;
                });
    }

    @Override
    public List<Schedule> findScheduleByEmployeeId(Long id) {
        return jdbcTemplate.query(SELECT_SCHEDULE_BY_EMPLOYEE_ID,
                new MapSqlParameterSource().addValue(EMPLOYEE_ID, id),
                resultSet -> {
                    List<Schedule> scheduleData = new ArrayList<>();
                    Set<Long> petData = new HashSet<>();
                    Set<Long> empData = new HashSet<>();
                    Set<EmployeeSkill> empSkill = new HashSet<>();
                    Schedule tempSchedule = null;
                    int scheduleRow = 0;
                    int countRow = 0;
                    while (resultSet.next()) {
                        tempSchedule = scheduleRowMapper.mapRow(resultSet, scheduleRow);

                        if (countRow == 0) {
                            scheduleData.add(tempSchedule);

                        } else if (tempSchedule.getId() != scheduleData.get(scheduleRow).getId()) {
                            //System.out.println(tempCustomerData.getId() + " " + customerData.get(custRow).getId());
                            scheduleData.get(scheduleRow).setPetIds(petData.stream().collect(Collectors.toList()));
                            scheduleData.get(scheduleRow).setEmployeeIds(empData.stream().collect(Collectors.toList()));
                            scheduleData.get(scheduleRow).setActivities(empSkill.stream().collect(Collectors.toSet()));
                            petData.clear();
                            empData.clear();
                            empSkill.clear();
                            scheduleData.add(tempSchedule);
                            //tempCustomerData = null;
                            //System.out.println(customerData.get(custRow).toString());
                            scheduleRow++;
                        }

                        petData.add(schedulePetsRowMapper.mapRow(resultSet, countRow).getPetsId());

                        empData.add(scheduleEmpRowMapper.mapRow(resultSet, countRow).getEmployeeId());
                        empSkill.add(EmployeeSkill.valueOf(scheduleActRowMapper.mapRow(resultSet, countRow++).getActivity()));
                    }
                    if (tempSchedule !=null) {
                        scheduleData.get(scheduleRow).setPetIds(petData.stream().collect(Collectors.toList()));
                        scheduleData.get(scheduleRow).setEmployeeIds(empData.stream().collect(Collectors.toList()));
                        scheduleData.get(scheduleRow).setActivities(empSkill.stream().collect(Collectors.toSet()));
                    }
                    return scheduleData;
                });
    }

    @Override
    public List<Schedule> findScheduleForCustomerId(Long id) {
        return jdbcTemplate.query(SELECT_SCHEDULE_FOR_CUSTOMER_ID,
                new MapSqlParameterSource().addValue(OWNER_ID, id),
                resultSet -> {
                    List<Schedule> scheduleData = new ArrayList<>();
                    Set<Long> petData = new HashSet<>();
                    Set<Long> empData = new HashSet<>();
                    Set<EmployeeSkill> empSkill = new HashSet<>();
                    Schedule tempSchedule = null;
                    int scheduleRow = 0;
                    int countRow = 0;
                    while (resultSet.next()) {
                        tempSchedule = scheduleRowMapper.mapRow(resultSet, scheduleRow);

                        if (countRow == 0) {
                            scheduleData.add(tempSchedule);

                        } else if (tempSchedule.getId() != scheduleData.get(scheduleRow).getId()) {
                            //System.out.println(tempCustomerData.getId() + " " + customerData.get(custRow).getId());
                            scheduleData.get(scheduleRow).setPetIds(petData.stream().collect(Collectors.toList()));
                            scheduleData.get(scheduleRow).setEmployeeIds(empData.stream().collect(Collectors.toList()));
                            scheduleData.get(scheduleRow).setActivities(empSkill.stream().collect(Collectors.toSet()));
                            petData.clear();
                            empData.clear();
                            empSkill.clear();
                            scheduleData.add(tempSchedule);
                            //tempCustomerData = null;
                            //System.out.println(customerData.get(custRow).toString());
                            scheduleRow++;
                        }

                        petData.add(schedulePetsRowMapper.mapRow(resultSet, countRow).getPetsId());

                        empData.add(scheduleEmpRowMapper.mapRow(resultSet, countRow).getEmployeeId());
                        empSkill.add(EmployeeSkill.valueOf(scheduleActRowMapper.mapRow(resultSet, countRow++).getActivity()));
                    }
                    if (tempSchedule !=null) {
                        scheduleData.get(scheduleRow).setPetIds(petData.stream().collect(Collectors.toList()));
                        scheduleData.get(scheduleRow).setEmployeeIds(empData.stream().collect(Collectors.toList()));
                        scheduleData.get(scheduleRow).setActivities(empSkill.stream().collect(Collectors.toSet()));
                    }
                    return scheduleData;
                });
    }


}
