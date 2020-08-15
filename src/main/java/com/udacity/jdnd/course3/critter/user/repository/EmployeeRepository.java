package com.udacity.jdnd.course3.critter.user.repository;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.data.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    //Group by logic was found in stackoverflow
    @Query("SELECT e from Employee e Left Join e.skills s Join e.daysAvailable d WHERE s in :es and d in :dl "
            + " GROUP BY e HAVING COUNT(s) = :skillSize" )
    List<Employee> findEmployeesByDaysAvailableAndSkills(@Param("es") Set<EmployeeSkill> es,
                                                         @Param("dl") Set<DayOfWeek> dl,
                                                         @Param("skillSize") long skillSize);

    @Query("SELECT e from Employee e Join e.skills s Join e.daysAvailable d WHERE s in :es and d in :dl ")
    List<Employee>findAllEmployees(@Param("es") Set<EmployeeSkill> es, @Param("dl") Set<DayOfWeek> dl);
}
