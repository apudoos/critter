package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.user.data.Customer;
import com.udacity.jdnd.course3.critter.user.data.CustomerData;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<CustomerDTO> getAllCustomers() {
        List<CustomerData> customerData = customerRepository.findAllCustomers();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (CustomerData data : customerData) {
            customerDTOS.add(convertCustomerDataToDTO(data));
        }
        return customerDTOS;
    }

    public CustomerDTO getOwnerByPet(long petId) {
        List<CustomerData> customerData = customerRepository.findCustomerByPetId(petId);
        CustomerDTO customerDTO = convertCustomerDataToDTO(customerData.get(0));
        return customerDTO;
    }

    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customerData = convertCustomerDTOToData(customerDTO);
        Long id = customerRepository.addCustomer(customerData);
        List<CustomerData> cd = customerRepository.findCustomerById(id);
        return convertCustomerDataToDTO(cd.get(0));
    }

    //Utility to convert the data
    private CustomerDTO convertCustomerDataToDTO(CustomerData customerData) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customerData, customerDTO);
        customerDTO.setPhoneNumber(customerData.getPhone());
        return customerDTO;
    }

    private Customer convertCustomerDTOToData(CustomerDTO customerDTO) {
        Customer customerData = new Customer();
        BeanUtils.copyProperties(customerDTO, customerData);
        customerData.setPhone(customerDTO.getPhoneNumber());
        return customerData;
    }

}
