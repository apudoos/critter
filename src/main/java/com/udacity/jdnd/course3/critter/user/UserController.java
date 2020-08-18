package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.user.data.Customer;
import com.udacity.jdnd.course3.critter.user.data.CustomerData;
import com.udacity.jdnd.course3.critter.user.data.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customerData = convertCustomerDTOToData(customerDTO);
        CustomerData cd = customerService.saveCustomer(customerData);
        return convertCustomerDataToDTO(cd);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerData> customerData = customerService.getAllCustomers();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (CustomerData data : customerData) {
            customerDTOS.add(convertCustomerDataToDTO(data));
        }
        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        CustomerData customerData = customerService.getOwnerByPet(petId);
        CustomerDTO customerDTO = convertCustomerDataToDTO(customerData);
        return customerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertEmpDTOToEmp(employeeDTO);
        employee = employeeService.saveEmployee(employee);
        return convertEmpToEmpDTO(employee);
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return convertEmpToEmpDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        DayOfWeek day = employeeDTO.getDate().getDayOfWeek();
        String dayOfWeek = day.getDisplayName(TextStyle.FULL, Locale.US);
        Set<DayOfWeek> dl = new HashSet<>();
        dl.add(DayOfWeek.valueOf(dayOfWeek.toUpperCase()));

        List <Employee> employee = employeeService.findEmployeesForService(employeeDTO.getSkills(), dl);
        List <EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee emp: employee) {
            employeeDTOS.add(convertEmpToEmpDTO(emp));
        }

        return employeeDTOS;

    }

    /*@GetMapping("/employee")
    public List<EmployeeDTO> getAllEmployee() {
        return employeeService.getAllEmployee();

    }*/

    //Utility to convert the customer data to DTO
    private CustomerDTO convertCustomerDataToDTO(CustomerData customerData) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customerData, customerDTO);
        customerDTO.setPhoneNumber(customerData.getPhone());
        return customerDTO;
    }

    //Utility to convert the DTO to customer data
    private Customer convertCustomerDTOToData(CustomerDTO customerDTO) {
        Customer customerData = new Customer();
        BeanUtils.copyProperties(customerDTO, customerData);
        customerData.setPhone(customerDTO.getPhoneNumber());
        return customerData;
    }

    //Utility to convert the Employee DTO to data
    private Employee convertEmpDTOToEmp(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    //Utility to convert the Employee data to DTO
    private EmployeeDTO convertEmpToEmpDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);

        return employeeDTO;
    }

}
