package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.user.data.Employee;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertEmpDTOToEmp(employeeDTO);
        employee = employeeRepository.save(employee);
        return convertEmpToEmpDTO(employee);
    }

    public EmployeeDTO getEmployee(Long employeeId) {
        Optional<Employee> optional = employeeRepository.findById(employeeId);

        if(optional.isPresent()) {
            Employee employee = optional.get();
            return convertEmpToEmpDTO(employee);
        } else {
            throw new EntityNotFoundException(employeeId.toString());
        }

    }

    public List<EmployeeDTO> getAllEmployee() {
        Set<EmployeeSkill> es = new HashSet<>();
        es.add(EmployeeSkill.FEEDING);
        Set<DayOfWeek> dl = new HashSet<>();
        dl.add(DayOfWeek.WEDNESDAY);
        List<Employee> employees = employeeRepository.findAllEmployees(es, dl);

        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for(Employee e1: employees) {
            employeeDTOS.add(convertEmpToEmpDTO(e1));
        }
        return employeeDTOS;

    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Optional<Employee> optional = employeeRepository.findById(employeeId);
        if(optional.isPresent()) {
            Employee employee = optional.get();
            employee.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
        } else {
            throw new EntityNotFoundException(employeeId.toString());
        }
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO) {
        DayOfWeek day = employeeDTO.getDate().getDayOfWeek();
        String dayOfWeek = day.getDisplayName(TextStyle.FULL, Locale.US);
        Set<DayOfWeek> dl = new HashSet<>();
        dl.add(DayOfWeek.valueOf(dayOfWeek.toUpperCase()));

        List<Employee> emp = employeeRepository.findEmployeesByDaysAvailableAndSkills(employeeDTO.getSkills(),
                dl, employeeDTO.getSkills().size());
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for(Employee e1: emp) {
            employeeDTOS.add(convertEmpToEmpDTO(e1));
        }
        return employeeDTOS;
    }

    private Employee convertEmpDTOToEmp(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    private EmployeeDTO convertEmpToEmpDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);

        return employeeDTO;
    }

}
