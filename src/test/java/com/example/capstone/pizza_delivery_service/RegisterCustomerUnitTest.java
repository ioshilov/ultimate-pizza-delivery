package com.example.capstone.pizza_delivery_service;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import com.example.capstone.pizza_delivery_service.entity.AuthGroupEntity;
import com.example.capstone.pizza_delivery_service.entity.CustomerEntity;
import com.example.capstone.pizza_delivery_service.entity.CustomersCredentialsEntity;
import com.example.capstone.pizza_delivery_service.mapper.CustomerMapper;
import com.example.capstone.pizza_delivery_service.model.Customer;
import com.example.capstone.pizza_delivery_service.repositories.CustomersRepository;
import com.example.capstone.pizza_delivery_service.service.ShoppingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RegisterCustomerUnitTest {
    @Mock
    private CustomersRepository customersRepository;
    @InjectMocks
    private ShoppingService shoppingService;

    @InjectMocks
    private CustomerMapper customerMapper;

    @Test
    public void testAddCustomer() {

        CustomerEntity aMockEntity = new CustomerEntity();
        CustomersCredentialsEntity customersCredentialsEntity = new CustomersCredentialsEntity(1, 1, "Johnson", "password");
        AuthGroupEntity authGroupEntity = new AuthGroupEntity(1, "ADMIN");

        aMockEntity.setName("Jenny");
        aMockEntity.setSurname("Johnson");
        aMockEntity.setDOB(LocalDate.now());
        aMockEntity.setMobile("7445566321");
        aMockEntity.setHomeAddress("Jenny'sAddress");
        aMockEntity.setEmail("jenny@email.com");
        aMockEntity.setCustomersCredentialsEntity(customersCredentialsEntity);
        List<AuthGroupEntity> authGroupEntityList = new ArrayList<>();
        authGroupEntityList.add(authGroupEntity);
        customersCredentialsEntity.setAuthGroupEntityList(authGroupEntityList);

        Customer aMockContact = customerMapper.mapCustomerEntityToModel(aMockEntity);

        when(customersRepository.save(any(CustomerEntity.class))).thenReturn(aMockEntity);

        CustomerEntity newContact = shoppingService.registerNewCustomer(aMockContact);

        assertNotNull(newContact);
        assertEquals("Jenny", newContact.getName());
        assertEquals("password", newContact.getCustomersCredentialsEntity().getPassword());
    }
}
