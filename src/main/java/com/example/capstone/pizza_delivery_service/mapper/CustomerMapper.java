package com.example.capstone.pizza_delivery_service.mapper;

import com.example.capstone.pizza_delivery_service.entity.AuthGroupEntity;
import com.example.capstone.pizza_delivery_service.entity.CustomerEntity;
import com.example.capstone.pizza_delivery_service.model.Customer;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class CustomerMapper {


  public Customer mapCustomerEntityToModel (CustomerEntity customerEntity){
        return new Customer(customerEntity.getId(),
                customerEntity.getName(),
                customerEntity.getSurname(),
                customerEntity.getMobile(),
                customerEntity.getDOB(),
                customerEntity.getEmail(),
                customerEntity.getHomeAddress(),
                customerEntity.getCustomersCredentialsEntity().getUsername(),
                customerEntity.getCustomersCredentialsEntity().getPassword(),
                customerEntity.getCustomersCredentialsEntity().getAuthGroupEntityList().stream().map(AuthGroupEntity::getAuthgroup).collect(Collectors.toList()));

    }

}
