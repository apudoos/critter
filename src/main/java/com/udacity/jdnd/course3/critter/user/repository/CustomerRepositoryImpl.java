package com.udacity.jdnd.course3.critter.user.repository;

import com.udacity.jdnd.course3.critter.pet.data.PetData;
import com.udacity.jdnd.course3.critter.user.data.Customer;
import com.udacity.jdnd.course3.critter.user.data.CustomerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String PET_ID = "petId";
    private static final String CUSTOMER_ID = "id";

    private static final String INSERT_CUSTOMER = "insert into customer (name, phone, notes) " +
            "values (:name, :phone, :notes) ";

    //private static final String SELECT_ALL_CUSTOMERS= "select * from customer";

    private static final String SELECT_ALL_CUSTOMERS = "select c.id, c.name, c.phone, c.notes, p.pet_id from customer c " +
            " left join pets p on c.id = p.owner_id ";

    private static final String SELECT_CUSTOMER_BY_ID = "select * from customer " +
            " where id = :" + CUSTOMER_ID;

    private static final String SELECT_CUSTOMER_BY_PET_ID = "select c.id, c.name, c.phone, c.notes, p.pet_id from customer c " +
            " join pets p on c.id = p.owner_id " +
            " where p.pet_id = :" + PET_ID;

    private static final RowMapper<Customer> customerRowMapper
            = new BeanPropertyRowMapper<>(Customer.class);

    private static final BeanPropertyRowMapper<CustomerData> customerDataRowMapper =
            new BeanPropertyRowMapper<>(CustomerData.class);

    private static final BeanPropertyRowMapper<PetData> petDataRowMapper =
            new BeanPropertyRowMapper<>(PetData.class);


    @Override
    public List<CustomerData> findAllCustomers() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_CUSTOMERS,
                resultSet -> {
                    List<CustomerData> customerData = new ArrayList<>();
                    List<Long> petData = new ArrayList<>();
                    CustomerData tempCustomerData = null;
                    int custRow = 0;
                    int petRow = 0;
                    while (resultSet.next()) {
                        tempCustomerData = customerDataRowMapper.mapRow(resultSet, custRow);

                        if (petRow == 0) {
                            customerData.add(tempCustomerData);

                        } else if (tempCustomerData.getId() != customerData.get(custRow).getId()) {

                            customerData.get(custRow).setPetIds(petData.stream().collect(Collectors.toList()));
                            petData.clear();
                            customerData.add(tempCustomerData);

                            custRow++;
                        }
                        petData.add(petDataRowMapper.mapRow(resultSet, petRow++).getPetId());
                    }
                    customerData.get(custRow).setPetIds(petData);
                    return customerData;
                });
    }

    @Override
    public Long addCustomer(Customer customer) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(customer);

        KeyHolder key = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_CUSTOMER,
                parameterSource,
                key
        );
        return key.getKey().longValue();
    }

    @Override
    public List<CustomerData> findCustomerByPetId(Long petId) {
        return namedParameterJdbcTemplate.query(SELECT_CUSTOMER_BY_PET_ID,
                new MapSqlParameterSource().addValue(PET_ID, petId),
                resultSet -> {
                    List<CustomerData> customerData = new ArrayList<>();
                    List<Long> petData = new ArrayList<>();
                    CustomerData tempCustomerData = null;
                    int custRow = 0;
                    int petRow = 0;
                    while (resultSet.next()) {
                        tempCustomerData = customerDataRowMapper.mapRow(resultSet, custRow);

                        if (petRow == 0) {
                            customerData.add(tempCustomerData);

                        } else if (tempCustomerData.getId() != customerData.get(custRow).getId()) {

                            customerData.get(custRow).setPetIds(petData.stream().collect(Collectors.toList()));
                            petData.clear();
                            customerData.add(tempCustomerData);

                            custRow++;
                        }
                        petData.add(petDataRowMapper.mapRow(resultSet, petRow++).getPetId());
                    }
                    customerData.get(custRow).setPetIds(petData);
                    return customerData;
                });
    }

    @Override
    public List<CustomerData> findCustomerById(Long id) {
        return namedParameterJdbcTemplate.query(SELECT_CUSTOMER_BY_ID,
                new MapSqlParameterSource().addValue(CUSTOMER_ID, id),
                customerDataRowMapper);
    }

}
