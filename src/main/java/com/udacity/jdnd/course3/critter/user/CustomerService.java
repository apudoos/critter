package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.user.data.Customer;
import com.udacity.jdnd.course3.critter.user.data.CustomerData;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public CustomerData saveCustomer(Customer customerData) {
        Long id = customerRepository.addCustomer(customerData);
        return customerRepository.findCustomerById(id).get(0);
    }

    public List<CustomerData> getAllCustomers() {
        return customerRepository.findAllCustomers();
    }

    public CustomerData getOwnerByPet(long petId) {
        List<CustomerData> customerData = customerRepository.findCustomerByPetId(petId);
        return customerData.get(0);
    }

}
