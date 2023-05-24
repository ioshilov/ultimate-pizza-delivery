package com.example.capstone.pizza_delivery_service.controller;


import com.example.capstone.pizza_delivery_service.entity.CustomerEntity;
import com.example.capstone.pizza_delivery_service.entity.CustomersCredentialsEntity;
import com.example.capstone.pizza_delivery_service.entity.FoodTypesEntity;
import com.example.capstone.pizza_delivery_service.mapper.FoodTypesMapper;
import com.example.capstone.pizza_delivery_service.mapper.ToppingsMapper;
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

import java.util.ArrayList;
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
         model.addAttribute("foodtypes",foodTypesEntities);
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
    public String addToCart (@PathVariable Integer ID, @RequestParam(value = "topping" , required = false) String[] toppings){
        logger.error("******************** TEST CART *****************");
        ToppingsMapper toppingsMapper=new ToppingsMapper();
        FoodTypesMapper foodTypesMapper=new FoodTypesMapper();
        List<Toppings> toppingsList=new ArrayList<>();
        if (toppings!=null){
            for (String topping:toppings
                 ) { toppingsList.add(toppingsMapper.mapToppingsEntityToModel(toppingsRepository.findByName(topping)));
            }
        }
        Dishes dishes=new Dishes(foodTypesMapper.mapFoodTypesEntityToModel(foodTypesRepository.findById(ID).get()));
        dishes.setToppings(toppingsList);
        orderCart.addDishes(dishes);
        logger.error("added to cart "+ dishes);
        logger.error("price "+ dishes.getSum());
        logger.error("******************** TEST CART ENDS *****************");
        return  "redirect:/";
    }

    @GetMapping(value = "/showordercart")
    public String showCart (Model model){
        logger.error("******************** CART SHOW *****************");
//        orderCart.getDishesList().stream().forEach(x->logger.error(x.toString()));
        model.addAttribute("dishes",orderCart.getDishesList());
        logger.error("******************** CART SHOW ENDS*****************");
        return  "ordercart";
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
