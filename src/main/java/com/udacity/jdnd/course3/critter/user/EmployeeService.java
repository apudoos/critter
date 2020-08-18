package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.user.data.Employee;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optional = employeeRepository.findById(employeeId);

        if (optional.isPresent()) {
            Employee employee = optional.get();
            return employee;
        } else {
            throw new EntityNotFoundException(employeeId.toString());
        }

    }

    /*public List<EmployeeDTO> getAllEmployee() {
        Set<EmployeeSkill> es = new HashSet<>();
        es.add(EmployeeSkill.FEEDING);
        Set<DayOfWeek> dl = new HashSet<>();
        dl.add(DayOfWeek.WEDNESDAY);
        List<Employee> employees = employeeRepository.findAllEmployees(es, dl);

        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee e1 : employees) {
            employeeDTOS.add(convertEmpToEmpDTO(e1));
        }
        return employeeDTOS;

    }*/

    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Optional<Employee> optional = employeeRepository.findById(employeeId);
        if (optional.isPresent()) {
            Employee employee = optional.get();
            employee.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
        } else {
            throw new EntityNotFoundException(employeeId.toString());
        }
    }

    public List<Employee> findEmployeesForService(Set<EmployeeSkill> employeeSkills, Set<DayOfWeek> dl) {

        List<Employee> emp = employeeRepository.findEmployeesByDaysAvailableAndSkills(employeeSkills,
                dl, employeeSkills.size());
        return emp;
    }

}
