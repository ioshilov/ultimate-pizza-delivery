package com.example.capstone.pizza_delivery_service.controller;
import com.example.capstone.pizza_delivery_service.security.UserPrincipal;
import com.example.capstone.pizza_delivery_service.entity.OrdersEntity;
import com.example.capstone.pizza_delivery_service.model.*;
import com.example.capstone.pizza_delivery_service.service.ShoppingService;
import com.example.capstone.pizza_delivery_service.service.DatabaseService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
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
    private final ShoppingService shoppingService;
    private final DatabaseService databaseService;
    private final Customer customer;
    private  OrderCart orderCart;
    private final OrderDetails orderDetails;


    public Controller(ShoppingService shoppingService, DatabaseService databaseService, Customer customer, OrderCart orderCart, OrderDetails orderDetails) {
        this.shoppingService = shoppingService;
        this.databaseService = databaseService;
        this.customer = customer;
        this.orderCart = orderCart;
        this.orderDetails = orderDetails;
    }

    @GetMapping(value = {"/", "/index"})
    public String getHomePage(Model model) {

        model.addAttribute("foodtypes", databaseService.getAllFoodTypes());
        model.addAttribute("toppingslist", databaseService.getAllToppings());
        model.addAttribute("customer", customer);
        addDefaultAttributes(model);
        logger.info("********************Your cart is full of " + orderCart.getDishesList().toString());

        return "index";
    }

    @GetMapping(value = "/customers")
    public String getAll(Model model) {
        List<Customer> customers = databaseService.getAllCustomers();
        model.addAttribute("customers", customers);
        addDefaultAttributes(model);
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
        addDefaultAttributes(model);
        return "myOrders-view";
    }

    @GetMapping(value = "/customers/{id}")
    public String getCustomerById(@PathVariable Integer id, Model model) {
        List<Customer> customerList=new ArrayList<>();
        customerList.add(databaseService.findCustomerById(id));
        model.addAttribute("customers", customerList);
        addDefaultAttributes(model);
        return "credentials-view";
    }

    @GetMapping(value = "/orders")
    public String getAllOrders(Model model) {
        model.addAttribute("orders", databaseService.getAllOrders());
        addDefaultAttributes(model);
        return "orders-view";
    }

    @GetMapping(value = "/delete/{ID}")
    public String deleteFromCart(@PathVariable Integer ID) {
        logger.info("********************" + ID+" Deleted from cart******************");
        orderCart.deleteDishes(ID);
        logger.info(orderCart.toString());
        return "redirect:/";
    }

    @PostMapping(value = "/pay")
    public String addToCart(@ModelAttribute(value = "orderDetails") OrderDetails orderDetails,
                            @RequestParam(value = "action", required = true) String action,
                            @AuthenticationPrincipal UserPrincipal user) {
        logger.error(orderDetails.toString());
        if (orderCart.getDishesList().isEmpty()) {
            return "redirect:/";
        }

        shoppingService.createOrder(orderCart, orderDetails, user, action);
        orderCart = new OrderCart();
        logger.info("************* Order - " + action + "**************");
        return "redirect:/";
    }


    @PostMapping(value = "/addtocart/{ID}")
    public String addToCart(@PathVariable Integer ID,
                            @RequestParam(value = "topping", required = false) String[] toppings) {
        Dishes dishes= shoppingService.createDishes(ID,toppings);
        orderCart.addDishes(dishes);
        logger.info("added to cart " + dishes);
        return "redirect:/";
    }

    public void addDefaultAttributes(Model model){
        model.addAttribute("orderCart", orderCart);
        model.addAttribute("dishes", orderCart.getDishesList());
        model.addAttribute("orderDetails", orderDetails);
    }


}
