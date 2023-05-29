package com.example.capstone.pizza_delivery_service.controller;
import com.example.capstone.pizza_delivery_service.model.*;
import com.example.capstone.pizza_delivery_service.service.CustomerService;
import com.example.capstone.pizza_delivery_service.service.DatabaseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {
    Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private  CustomerService customerService;
    @Autowired
    private  DatabaseService databaseService;
    @Autowired
    private Customer customer;
    @Autowired
    private OrderCart orderCart;
    @Autowired
    private OrderDetails orderDetails;


    @GetMapping(value = {"/", "/index"})
    public String getHomePage(Model model) {

        logger.info("********************INDEX*****************");
        model.addAttribute("foodtypes", databaseService.getAllFoodTypes());
        model.addAttribute("toppingslist", databaseService.getAllToppings());
        model.addAttribute("dishes", orderCart.getDishesList());
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("customer", customer);
        model.addAttribute("orderCart", orderCart);
        logger.warn("********************Your cart is full of " + orderCart.getDishesList().toString());
        return "index";
    }

    @GetMapping(value = "/customers")
    public String getAll(Model model) {
        List<Customer> customers = databaseService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customers-view";
    }

    @GetMapping(value = "/customers/{ID}")
    public String getCustomerById(@PathVariable Integer id, Model model) {
        model.addAttribute("customers", databaseService.findCustomerById(id));
        return "credentials-view";
    }

    @GetMapping(value = "/orders")
    public String getAllOrders(Model model) {
        model.addAttribute("orders", databaseService.getAllOrders());
        return "orders-view";
    }

    @GetMapping(value = "/delete/{ID}")
    public String deleteFromCart(@PathVariable Integer ID) {
        logger.warn("********************Delete ID " + ID+"******************");
        orderCart.deleteDishes(ID);
        logger.info(orderCart.toString());
        return "redirect:/";
    }

    @PostMapping(value = "/pay")
    public String addToCart(@ModelAttribute(value = "orderDetails") OrderDetails orderDetails,
                            @RequestParam(value = "action", required = true) String action,
                            @AuthenticationPrincipal UserPrincipal user) {
        logger.info("*************Testing payment**************");
        logger.error(orderDetails.toString());
        if (orderCart.getDishesList().isEmpty()) {
            return "redirect:/";
        }
        logger.info("*************" + action + "**************");
        customerService.createOrder(orderCart, orderDetails, user, action);
        orderCart = new OrderCart();
        return "redirect:/";
    }


    @PostMapping(value = "/addtocart/{ID}")
    public String addToCart(@PathVariable Integer ID,
                            @RequestParam(value = "topping", required = false) String[] toppings) {
        Dishes dishes=customerService.createDishes(ID,toppings);
        orderCart.addDishes(dishes);
        logger.warn("added to cart " + dishes);
        return "redirect:/";
    }

    @GetMapping(value = "/showordercart")
    public String showCart(Model model) {
        model.addAttribute("dishes", orderCart.getDishesList());
        return "ordercart";
    }

    @PostMapping(value = "/signup")
    public String signUpNewUser(@Valid @ModelAttribute(value = "customer") Customer customer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.error("*************FORM ERRORRR********************");
            model.addAttribute("customer", customer);
            model.addAttribute("name", customer.getName());
            model.addAttribute("surname", customer.getSurname());
            model.addAttribute("mobile", customer.getMobile());
            model.addAttribute("homeAddress", customer.getHomeAddress());
            model.addAttribute("email", customer.getEmail());
            model.addAttribute("username", customer.getSurname());
            model.addAttribute("password", customer.getPassword());
            model.addAttribute("DOB", customer.getDOB());
            if (databaseService.checkNewCustomerUsername(customer.getUsername())){
                model.addAttribute("errorUsername","Username exist");
            }
            return "signup";
        }
        customerService.registerNewCustomer(customer);
        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String getLogin(Model model) {
        model.addAttribute("customer", customer);
        return "login";
    }

    @GetMapping(value = "/signup")
    public String getSignup(Model model) {
        model.addAttribute("customer", customer);
        return "signup";
    }


}
