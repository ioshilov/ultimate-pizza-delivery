package com.example.capstone.pizza_deilevery_service.controller;


import com.example.capstone.pizza_deilevery_service.model.Customer;
import com.example.capstone.pizza_deilevery_service.repositories.CustomersRepository;
import com.example.capstone.pizza_deilevery_service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.*;

import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping ("/")
public class Controller {

    private final CustomerService customerService;

    @Autowired
    public Controller(CustomerService customerService) {
        this.customerService = customerService;

    }

    @GetMapping(value={"/", "/index"})
    public String getHomePage(Model model){
        return "index";
    }
    @GetMapping(value = "/customers")
    public String getAll (Model model){
        List<Customer> customers= customerService.getAllCustomers();
        model.addAttribute("customers",customers);

    return "guests-view";
    }
}
