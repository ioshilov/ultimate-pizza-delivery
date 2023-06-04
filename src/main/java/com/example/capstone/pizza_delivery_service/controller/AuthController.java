package com.example.capstone.pizza_delivery_service.controller;


import com.example.capstone.pizza_delivery_service.model.Customer;

import com.example.capstone.pizza_delivery_service.model.OrderCart;
import com.example.capstone.pizza_delivery_service.model.OrderDetails;
import com.example.capstone.pizza_delivery_service.service.ShoppingService;
import com.example.capstone.pizza_delivery_service.service.DatabaseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final ShoppingService shoppingService;
    private final DatabaseService databaseService;
    private final Customer customer;
    private final OrderCart orderCart;
    private final OrderDetails orderDetails;

    @Autowired
    public AuthController(ShoppingService shoppingService, DatabaseService databaseService, Customer customer, OrderCart orderCart, OrderDetails orderDetails) {
        this.shoppingService = shoppingService;
        this.databaseService = databaseService;
        this.customer = customer;
        this.orderCart = orderCart;
        this.orderDetails = orderDetails;
    }

    @PostMapping(value = "/signup")
    public String signUpNewUser(@Valid @ModelAttribute(value = "customer") Customer customer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || (databaseService.checkNewCustomerUsername(customer.getUsername()))) {
            logger.error("*************Sign up form ERROR********************");

            if (databaseService.checkNewCustomerUsername(customer.getUsername())) {
                model.addAttribute("errorUsername", "Username exist");
            }
            model.addAttribute("customer", customer);
            addDefaultAttributes(model);
            return "signup";
        }
        addDefaultAttributes(model);
        shoppingService.registerNewCustomer(customer);
        logger.info("************* New user signed up ********************");
        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String getLogin(Model model) {
        addDefaultAttributes(model);
        return "login";
    }

    @GetMapping(value = "/signup")
    public String getSignup(Model model) {
        model.addAttribute("customer", customer);
        addDefaultAttributes(model);
        return "signup";
    }

    public Model addDefaultAttributes(Model model) {
        model.addAttribute("orderCart", orderCart);
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("dishes", orderCart.getDishesList());
        return model;
    }

}
