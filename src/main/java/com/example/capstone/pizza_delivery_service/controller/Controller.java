package com.example.capstone.pizza_delivery_service.controller;


import com.example.capstone.pizza_delivery_service.entity.CustomerEntity;
import com.example.capstone.pizza_delivery_service.entity.CustomersCredentialsEntity;
import com.example.capstone.pizza_delivery_service.entity.FoodTypesEntity;
import com.example.capstone.pizza_delivery_service.model.*;
import com.example.capstone.pizza_delivery_service.repositories.CustomersRepository;
import com.example.capstone.pizza_delivery_service.repositories.FoodTypesRepository;
import com.example.capstone.pizza_delivery_service.repositories.ToppingsRepository;
import com.example.capstone.pizza_delivery_service.service.CustomerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping ("/")
public class Controller {

    Logger logger = LoggerFactory.getLogger(Controller.class);

    private final CustomerService customerService;
    @Autowired
    private  CustomersRepository customersRepository;
    @Autowired
    private  FoodTypesRepository foodTypesRepository;

    @Autowired
    private ToppingsRepository toppingsRepository;


    @Autowired
    private OrderCart orderCart;

    @Autowired
    public Controller(CustomerService customerService) {
        this.customerService = customerService;
    }








    @GetMapping(value={"/", "/index"})
    public String getHomePage(Model model){
        logger.error("********************INDEX*****************");
        List< FoodTypesEntity> foodTypesEntities =foodTypesRepository.findAll();
        model.addAttribute("title_1",foodTypesEntities.get(1).getName());
        model.addAttribute("description_1",foodTypesEntities.get(1).getDescription());
        model.addAttribute("toppingslist",toppingsRepository.findAll().stream().map(x->new Toppings(x.getName(),x.getPrice())).toList());
        return "index";
    }
    @GetMapping(value = "/customers")
    public String getAll (Model model){
        List<Customer> customers= customerService.getAllCustomers();
        model.addAttribute("customers",customers);
    return "guests-view";
    }


    @PostMapping(value= "/addtocart/{ID}")
    public String addToCart (@PathVariable Integer ID, @Valid Dishes dishes, BindingResult bindingResult){
        logger.error("******************** TEST CART *****************");
//        logger.error(dishes.toString());
        orderCart.addDishes(new Dishes(ID,dishes.getJalapeno(),dishes.getPepper(),dishes.getCheese()));
        logger.error("added to cart"+ ID + " " + dishes.getJalapeno() + " "+ dishes.getPepper() + " "+ dishes.getCheese() + " ");
        logger.error("******************** TEST CART ENDS *****************");
        return  "redirect:/";
    }

    @GetMapping(value = "/showordercart")
    public String showCart (){
        logger.error("******************** CART FULL OF *****************");
        orderCart.getDishesList().stream().forEach(x->logger.error(x.toString()));
        logger.error("******************** CART ENDS*****************");
        return  "redirect:/";
    }

    @GetMapping(value = "/customers/credentials")
    public String getCustomerCredentials (Model model){
        CustomerEntity customerEntity=customersRepository.findById(1).stream().findFirst().get();

        Customer customers= new Customer(customerEntity.getId(),customerEntity.getName(),customerEntity.getSurname(),customerEntity.getMobile(),customerEntity.getDOB(),customerEntity.getEmail(),customerEntity.getHomeAddress());
        CustomersCredentialsEntity customersCredentialsEntity=customerEntity.getCustomersCredentialsEntity();
        CustomersCredentials customersCredentials=new CustomersCredentials(customersCredentialsEntity.getId(),customersCredentialsEntity.getLogin(),customersCredentialsEntity.getPassword(),customersCredentialsEntity.getRole());
        model.addAttribute("customers",customers);
        model.addAttribute("customerscredentials",customersCredentials);

        return "credentials-view";
    }
}
