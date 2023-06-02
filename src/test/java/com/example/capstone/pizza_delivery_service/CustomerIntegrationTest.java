package com.example.capstone.pizza_delivery_service;

import com.example.capstone.pizza_delivery_service.entity.CustomerEntity;
import com.example.capstone.pizza_delivery_service.model.Customer;
import com.example.capstone.pizza_delivery_service.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CustomerIntegrationTest {
    @Autowired
    private CustomerService customerService;

    @Test
    public void testAddCustomer() {

        // Create a contact
        Customer aContact = new Customer();
        aContact.setName("Jenny");
        aContact.setSurname("Johnson");
        aContact.setDOB(LocalDate.now());
        aContact.setMobile("7445566321");
        aContact.setHomeAddress("JennysAddress");
        aContact.setEmail("jenny@email.com");
        aContact.setPassword("Johnson");
        aContact.setUsername("Johnson");


        // Test adding the contact
        CustomerEntity newContact = customerService.registerNewCustomer(aContact);


        // Verify the addition
        assertNotNull(newContact);
        assertNotNull(newContact.getId());
        assertEquals("Jenny", newContact.getName());
        assertEquals("jenny@email.com", newContact.getEmail());

    }
}

