package com.example.capstone.pizza_delivery_service.service;

import com.example.capstone.pizza_delivery_service.controller.Controller;
import com.example.capstone.pizza_delivery_service.entity.AuthGroupEntity;
import com.example.capstone.pizza_delivery_service.entity.CustomerEntity;
import com.example.capstone.pizza_delivery_service.entity.CustomersCredentialsEntity;
import com.example.capstone.pizza_delivery_service.model.Customer;
import com.example.capstone.pizza_delivery_service.repositories.CustomersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    Logger logger = LoggerFactory.getLogger(Controller.class);
    private final CustomersRepository customersRepository;


    @Autowired
    public AccountService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public CustomerEntity registerNewCustomer(Customer customer) {

        CustomersCredentialsEntity credentials = new CustomersCredentialsEntity();
        CustomerEntity customerEntity = new CustomerEntity();
        AuthGroupEntity authGroupEntity = new AuthGroupEntity();

        authGroupEntity.setAuthgroup("CUSTOMER");
        List<AuthGroupEntity> authGroupEntityList = new ArrayList<>();
        authGroupEntityList.add(authGroupEntity);

        credentials.setUsername(customer.getUsername());
        credentials.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));
        credentials.setAuthGroupEntityList(authGroupEntityList);
        credentials.setCustomerEntity(customerEntity);

        customerEntity.setName(customer.getName());
        customerEntity.setSurname(customer.getSurname());
        customerEntity.setMobile(customer.getMobile());
        customerEntity.setHomeAddress(customer.getHomeAddress());
        customerEntity.setDOB(customer.getDOB());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setCustomersCredentialsEntity(credentials);

        logger.info("********************Service. Customer to DB  " + customerEntity + "***************");
        return customersRepository.save(customerEntity);
    }
}


