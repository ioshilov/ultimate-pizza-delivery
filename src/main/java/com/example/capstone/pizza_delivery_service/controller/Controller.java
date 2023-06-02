package com.example.capstone.pizza_delivery_service.controller;
import com.example.capstone.pizza_delivery_service.security.UserPrincipal;
import com.example.capstone.pizza_delivery_service.entity.OrdersEntity;
import com.example.capstone.pizza_delivery_service.model.*;
import com.example.capstone.pizza_delivery_service.service.CustomerService;
import com.example.capstone.pizza_delivery_service.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;


import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
@SessionScope
@RequestMapping("/")
public class Controller {
    Logger logger = LoggerFactory.getLogger(Controller.class);


    private final CustomerService customerService;

    private final DatabaseService databaseService;

    private final Customer customer;

    private  OrderCart orderCart;

    private final OrderDetails orderDetails;

    @Autowired
    public Controller(CustomerService customerService, DatabaseService databaseService, Customer customer, OrderCart orderCart, OrderDetails orderDetails) {
        this.customerService = customerService;
        this.databaseService = databaseService;
        this.customer = customer;
        this.orderCart = orderCart;
        this.orderDetails = orderDetails;
    }

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
        model.addAttribute("orderCart", orderCart);
        model.addAttribute("dishes", orderCart.getDishesList());
        model.addAttribute("orderDetails", orderDetails);
        return "customers-view";
    }

    @GetMapping(value = "/myOrders")
    public String getMyOrders(Model model) {
        List<Customer> customers = databaseService.getAllCustomers();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user= auth.getName();
        List<OrdersEntity> list= databaseService.findOrdersByUsername(user);

        model.addAttribute("orders", list);
        model.addAttribute("customers", customers);
        model.addAttribute("orderCart", orderCart);
        model.addAttribute("dishes", orderCart.getDishesList());
        model.addAttribute("orderDetails", orderDetails);
        return "myOrders-view";
    }

    @GetMapping(value = "/customers/{id}")
    public String getCustomerById(@PathVariable Integer id, Model model) {
        List<Customer> customerList=new ArrayList<>();
        customerList.add(databaseService.findCustomerById(id));
        model.addAttribute("customers", customerList);
        model.addAttribute("orderCart", orderCart);
        model.addAttribute("dishes", orderCart.getDishesList());
        model.addAttribute("orderDetails", orderDetails);
        return "credentials-view";
    }

    @GetMapping(value = "/orders")
    public String getAllOrders(Model model) {
        model.addAttribute("orders", databaseService.getAllOrders());
        model.addAttribute("orderCart", orderCart);
        model.addAttribute("dishes", orderCart.getDishesList());
        model.addAttribute("orderDetails", orderDetails);
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


}
