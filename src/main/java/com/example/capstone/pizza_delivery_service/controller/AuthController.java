package com.example.capstone.pizza_delivery_service.controller;

import com.example.capstone.pizza_delivery_service.model.*;
import com.example.capstone.pizza_delivery_service.service.CustomerService;
import com.example.capstone.pizza_delivery_service.service.DatabaseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

@org.springframework.stereotype.Controller
@SessionScope
@RequestMapping("/")
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);


    private final CustomerService customerService;

    private final DatabaseService databaseService;

    private final Customer customer;

    private  OrderCart orderCart;

    private final OrderDetails orderDetails;

    @Autowired
    public AuthController(CustomerService customerService, DatabaseService databaseService, Customer customer, OrderCart orderCart, OrderDetails orderDetails) {
        this.customerService = customerService;
        this.databaseService = databaseService;
        this.customer = customer;
        this.orderCart = orderCart;
        this.orderDetails = orderDetails;
    }


    @PostMapping(value = "/signup")
    public String signUpNewUser(@Valid @ModelAttribute(value = "customer") Customer customer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()||(databaseService.checkNewCustomerUsername(customer.getUsername()))) {
            logger.error("*************FORM ERRORRR********************");
            model.addAttribute("customer", customer);
            if (databaseService.checkNewCustomerUsername(customer.getUsername())){
                model.addAttribute("errorUsername","Username exist");
            }
            model.addAttribute("orderCart", orderCart);
            model.addAttribute("dishes", orderCart.getDishesList());
            model.addAttribute("orderDetails", orderDetails);
            return "signup";
        }
        logger.error("*************FORM Correct********************");
        model.addAttribute("orderCart", orderCart);
        model.addAttribute("dishes", orderCart.getDishesList());
        model.addAttribute("orderDetails", orderDetails);

        customerService.registerNewCustomer(customer);
        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String getLogin(Model model) {
        model.addAttribute("customer", customer);
        model.addAttribute("orderCart", orderCart);
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("dishes", orderCart.getDishesList());
        return "login";
    }

    @GetMapping(value = "/signup")
    public String getSignup(Model model) {
        model.addAttribute("customer", customer);
        model.addAttribute("orderCart", orderCart);
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("dishes", orderCart.getDishesList());
        return "signup";
    }


}
