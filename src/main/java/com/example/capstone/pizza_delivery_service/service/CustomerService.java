package com.example.capstone.pizza_delivery_service.service;

import com.example.capstone.pizza_delivery_service.model.Customer;
import com.example.capstone.pizza_delivery_service.repositories.CustomersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomersRepository customersRepository;

    public CustomerService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }
    public List<Customer> getAllCustomers() {

        return customersRepository.findAll().stream().map(x->new Customer(x.getId(),x.getName(),x.getSurname(),x.getMobile(),x.getDOB(),x.getEmail(),x.getHomeAddress())).collect(Collectors.toList());
    }

    public Customer getCustomerCredentialsbyID(){
        return customersRepository.findById(1).stream().map(x->new Customer(x.getId(),x.getName(),x.getSurname(),x.getMobile(),x.getDOB(),x.getEmail(),x.getHomeAddress())).limit(1).findFirst().get();

    }

}
