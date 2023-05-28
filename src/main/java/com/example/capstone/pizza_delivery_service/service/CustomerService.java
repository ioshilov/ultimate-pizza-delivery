package com.example.capstone.pizza_delivery_service.service;

import com.example.capstone.pizza_delivery_service.controller.Controller;
import com.example.capstone.pizza_delivery_service.entity.*;
import com.example.capstone.pizza_delivery_service.model.Customer;
import com.example.capstone.pizza_delivery_service.model.Dishes;
import com.example.capstone.pizza_delivery_service.model.OrderCart;
import com.example.capstone.pizza_delivery_service.model.OrderDetails;
import com.example.capstone.pizza_delivery_service.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@SessionScope
@Component
public class CustomerService {

    Logger logger = LoggerFactory.getLogger(Controller.class);
    private final CustomersRepository customersRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private OrderCartRepository orderCartRepository;
    @Autowired
    private DishesRepository dishesRepository;
    @Autowired

    private ToppingsRepository toppingsRepository;

    @Autowired

    private FoodTypesRepository foodTypesRepository;




    public CustomerService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

//    public List<Customer> getAllCustomers() {
//
//        return customersRepository.findAll().stream().map(x -> new Customer(x.getId(), x.getName(), x.getSurname(), x.getMobile(), x.getDOB(), x.getEmail(), x.getHomeAddress())).collect(Collectors.toList());
//    }
//
//    public Customer getCustomerCredentialsbyID() {
//        return customersRepository.findById(1).stream().map(x -> new Customer(x.getId(), x.getName(), x.getSurname(), x.getMobile(), x.getDOB(), x.getEmail(), x.getHomeAddress())).limit(1).findFirst().get();
//
//    }

    public void createOrder(OrderCart orderCart, OrderDetails orderDetails) {
        OrdersEntity ordersEntity = new OrdersEntity();
        ordersEntity.setLocalDate(LocalDate.now());
        ordersEntity.setSum(orderCart.getDishesList().stream().map(Dishes::getSum).reduce(BigDecimal.ZERO, BigDecimal::add));
        ordersRepository.save(ordersEntity);
        Integer ordersEntityID = ordersEntity.getId();
        logger.info("ORDER ID =" + ordersEntityID.toString());
        logger.error("**********Service  Before Cart fill *****************");
        OrderCartEntity orderCartEntity= fillOrderCartWithDishesAndSave(orderCart);

        OrderDetailsEntity orderDetailsEntity = new OrderDetailsEntity();
        orderDetailsEntity.setDeliveryName(orderDetails.getName());
        orderDetailsEntity.setDeliveryMobile(orderDetails.getMobile());
        orderDetailsEntity.setDeliveryPrice(BigDecimal.valueOf(1500));
        orderDetailsEntity.setDeliveryAddress(orderDetails.getAddress());
        orderDetailsEntity.setDeliveryComments(orderDetails.getComments());
        orderDetailsEntity.setOrderCartEntity(orderCartEntity);
        orderDetailsEntity.setOrdersEntity(ordersEntity);
        orderDetailsRepository.save(orderDetailsEntity);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        if (!auth.getPrincipal().toString().equals("anonymousUser")){
        logger.error("**********Customer: "+ auth.getPrincipal()+"*****************");}
        else logger.error("**********Customer anonymous  *****************");

        logger.error("**********Service  VICTORY Order details saved *****************");

    }

    public OrderCartEntity fillOrderCartWithDishesAndSave(OrderCart orderCart) {
        logger.error("**********Service  Topping repo *****************");
        List<ToppingsEntity> toppingsEntityList = toppingsRepository.findAll();
        logger.error("**********Service  Dishes repo *****************");
        logger.error("**********Service  STAGE  1 *****************");
        OrderCartEntity orderCartEntity=new OrderCartEntity();

        for (var dish : orderCart.getDishesList()
        ) {

//            if (dish.getToppings() != null) {
                logger.error("**********Service  STAGE  2 *****************");
                DishesEntity dishesEntity=new DishesEntity();
                String foodname=dish.getFoodType().getName();
                FoodTypesEntity foodTypesEntity=foodTypesRepository.findByName(foodname);
                dishesEntity.setFoodTypesEntity(foodTypesEntity);
                logger.error("**********"+foodTypesRepository.findByName(dish.getFoodType().getName())+ " *****************");
                logger.error("**********Service  STAGE  3 *****************");
                dish.getToppings().forEach(x -> {
                            logger.error("**********Service  STAGE  foreach 1 *****************");
                    ToppingsEntity toppingsEntity = toppingsEntityList.stream().filter(topping -> topping.getName().equals(x.getName())).findFirst().get();
                            logger.error(toppingsEntity.getName());
                            logger.error("**********Service  STAGE  foreach 2 *****************");

                            dishesEntity.addToppings(toppingsEntity);
                            logger.error("**********Service  STAGE  foreach 3 *****************");
                            logger.error(dishesEntity.toString());
                }
                );
                logger.error("**********Service  STAGE  4 *****************");
                 dishesRepository.save(dishesEntity);

                logger.error("**********Service  STAGE  4 Dishes Saved *****************");
                orderCartEntity.addDishes(dishesEntity);
            logger.error("**********Service  STAGE  4 add dish " + dishesEntity + " to cart *****************");
//            }


        }
        orderCartRepository.save(orderCartEntity);
        logger.error("**********Service  VICTORY *****************");
        return orderCartEntity;


    }
}
