package com.example.capstone.pizza_delivery_service.controller;


import com.example.capstone.pizza_delivery_service.entity.*;
import com.example.capstone.pizza_delivery_service.mapper.FoodTypesMapper;
import com.example.capstone.pizza_delivery_service.mapper.ToppingsMapper;
import com.example.capstone.pizza_delivery_service.model.*;
import com.example.capstone.pizza_delivery_service.repositories.*;
import com.example.capstone.pizza_delivery_service.service.CustomerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {

    Logger logger = LoggerFactory.getLogger(Controller.class);

    private final CustomerService customerService;
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private FoodTypesRepository foodTypesRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CustomersCredentialsRepository customersCredentialsRepository;

    @Autowired
    private ToppingsRepository toppingsRepository;
    @Autowired
    private Customer customer;


    @Autowired
    private OrderCart orderCart;

    @Autowired
    private OrderDetails orderDetails;


    @Autowired
    public Controller(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = {"/", "/index"})
    public String getHomePage(Model model) {

        logger.info("********************INDEX*****************");
        List<FoodTypesEntity> foodTypesEntities = foodTypesRepository.findAll();
        model.addAttribute("foodtypes", foodTypesEntities);
        model.addAttribute("toppingslist", toppingsRepository.findAll().stream().map(x -> new Toppings(x.getName(), x.getPrice())).toList());
        model.addAttribute("dishes", orderCart.getDishesList());
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("customer", customer);
        model.addAttribute("orderCart", orderCart);
        logger.warn("********************Your cart is full of " + orderCart.getDishesList().toString());
        return "index";
    }

    @GetMapping(value = "/customers")
    public String getAll(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "guests-view";
    }

    @GetMapping(value = "/customers/{ID}")
    public String getCustomerById(@PathVariable Integer ID, Model model) {
        CustomerEntity customer = customersRepository.findById(ID).get();
        model.addAttribute("customers", customer);
        return "credentials-view";
    }

    @GetMapping(value = "/orders")
    public String getAllOrders(Model model) {
        List<OrdersEntity> ordersEntityList = ordersRepository.findAll();
        model.addAttribute("orders", ordersEntityList);
        return "orders-view";
    }


    @GetMapping(value = "/delete/{ID}")
    public String deleteFromCart(@PathVariable Integer ID) {
        logger.error("********************Delete ID " + ID);
        orderCart.deleteDishes(ID);
        logger.error(orderCart.toString());
        return "redirect:/";
    }

    @PostMapping(value = "/pay")
    public String addToCart(@ModelAttribute(value = "orderDetails") OrderDetails orderDetails, @RequestParam(value = "action", required = true) String action, @AuthenticationPrincipal UserPrincipal user) {
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
    public String addToCart(@PathVariable Integer ID, @RequestParam(value = "topping", required = false) String[] toppings) {
        logger.info("******************** TEST CART *****************");
        ToppingsMapper toppingsMapper = new ToppingsMapper();
        FoodTypesMapper foodTypesMapper = new FoodTypesMapper();
        List<Toppings> toppingsList = new ArrayList<>();
        if (toppings != null) {
            for (String topping : toppings
            ) {
                toppingsList.add(toppingsMapper.mapToppingsEntityToModel(toppingsRepository.findByName(topping)));
            }
        }
        Dishes dishes = new Dishes(foodTypesMapper.mapFoodTypesEntityToModel(foodTypesRepository.findById(ID).get()));
        dishes.setToppings(toppingsList);
        orderCart.addDishes(dishes);
        logger.error("added to cart " + dishes);
        logger.error("price " + dishes.getSum());
        logger.info("******************** TEST CART ENDS *****************");
        return "redirect:/";
    }

    @GetMapping(value = "/showordercart")
    public String showCart(Model model) {
        logger.info("******************** CART SHOW *****************");
        model.addAttribute("dishes", orderCart.getDishesList());
        logger.error("******************** CART SHOW ENDS*****************");
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

            return "signup";
        }


        CustomersCredentialsEntity customersCredentialsEntityFromDatabase = customersCredentialsRepository.findByUsername(customer.getUsername());
        if (customersCredentialsEntityFromDatabase != null) return "error";

        CustomersCredentialsEntity customersCredentialsEntity = new CustomersCredentialsEntity();
        CustomerEntity customerEntity = new CustomerEntity();
        AuthGroupEntity authGroupEntity = new AuthGroupEntity();

        authGroupEntity.setAuthgroup("CUSTOMER");
        List<AuthGroupEntity> authGroupEntityList = new ArrayList<>();
        authGroupEntityList.add(authGroupEntity);

        logger.warn("******************** Customer2DB  " + authGroupEntityList.toString() + "***************");

        authGroupEntity.setCustomersCredentialsEntity(customersCredentialsEntity);

        customersCredentialsEntity.setUsername(customer.getUsername());
        customersCredentialsEntity.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));
        customersCredentialsEntity.setAuthGroupEntityList(authGroupEntityList);
        customersCredentialsEntity.setCustomerEntity(customerEntity);
//        customersCredentialsEntity.setCustomerid(customerEntity.getId());

        logger.warn("******************** Customer2DB  " + customersCredentialsEntity + "***************");

        customerEntity.setName(customer.getName());
        customerEntity.setSurname(customer.getSurname());
        customerEntity.setMobile(customer.getMobile());
        customerEntity.setHomeAddress(customer.getHomeAddress());
        customerEntity.setDOB(customer.getDOB());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setCustomersCredentialsEntity(customersCredentialsEntity);

        logger.warn("******************** Customer2DB  " + customerEntity + "***************");

        customersRepository.save(customerEntity);

        logger.warn("**********New customer signed in*********");
        return "redirect:/";
    }


    @GetMapping(value = "/login")
    public String getlogin(Model model) {
        model.addAttribute("customer", customer);
        return "login";
    }

    @GetMapping(value = "/signup")
    public String getSignup(Model model) {
        model.addAttribute("customer", customer);
        return "signup";
    }


}
