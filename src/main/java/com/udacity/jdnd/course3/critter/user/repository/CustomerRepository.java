package com.udacity.jdnd.course3.critter.user.repository;

import com.udacity.jdnd.course3.critter.schedule.data.Schedule;
import com.udacity.jdnd.course3.critter.user.data.Customer;
import com.udacity.jdnd.course3.critter.user.data.CustomerData;

import java.util.List;

public interface CustomerRepository {
    List<CustomerData> findAllCustomers();

    Long addCustomer(Customer customer);

    List<CustomerData> findCustomerByPetId(Long petId);

    List<CustomerData> findCustomerById(Long id);

}
