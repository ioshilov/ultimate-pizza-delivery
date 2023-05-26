package com.example.capstone.pizza_delivery_service.service;

import com.example.capstone.pizza_delivery_service.controller.Controller;
import com.example.capstone.pizza_delivery_service.entity.*;
import com.example.capstone.pizza_delivery_service.model.*;
import com.example.capstone.pizza_delivery_service.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<Customer> getAllCustomers() {

        return customersRepository.findAll().stream().map(x -> new Customer(x.getId(), x.getName(), x.getSurname(), x.getMobile(), x.getDOB(), x.getEmail(), x.getHomeAddress())).collect(Collectors.toList());
    }

    public Customer getCustomerCredentialsbyID() {
        return customersRepository.findById(1).stream().map(x -> new Customer(x.getId(), x.getName(), x.getSurname(), x.getMobile(), x.getDOB(), x.getEmail(), x.getHomeAddress())).limit(1).findFirst().get();

    }

    public void createOrder(OrderCart orderCart, OrderDetails orderDetails) {
        OrdersEntity ordersEntity = new OrdersEntity();
        ordersEntity.setLocalDate(LocalDate.now());
        ordersEntity.setSum(orderCart.getDishesList().stream().map(Dishes::getSum).reduce(BigDecimal.ZERO, BigDecimal::add));
        ordersRepository.save(ordersEntity);
        Integer ordersEntityID = ordersEntity.getId();
        logger.info("ORDER ID =" + ordersEntityID.toString());
        logger.error("**********Service  Before Cart fill *****************");
        fillOrderCartWithDishes(orderCart);

//        orderCart.getDishesList().forEach(x -> {
//        });

//
//        OrderDetailsEntity orderDetailsEntity = new OrderDetailsEntity();
//        orderDetailsEntity.setDeliveryName(orderDetails.getName());
//        orderDetailsEntity.setDeliveryMobile(orderDetails.getMobile());
//        orderDetailsEntity.setDeliveryPrice(BigDecimal.valueOf(1500));
//        orderDetailsEntity.setDeliveryComments(orderDetails.getComments());


    }

    public void fillOrderCartWithDishes(OrderCart orderCart) {
        logger.error("**********Service  Topping repo *****************");
        List<ToppingsEntity> toppingsEntityList = toppingsRepository.findAll();
        logger.error("**********Service  Dishes repo *****************");
//        List<DishesEntity> dishesEntities = dishesRepository.findAll();
//        List<Integer> dishesID = new ArrayList<>();
        logger.error("**********Service  STAGE  1 *****************");


        for (var dish : orderCart.getDishesList()
        ) {
            if (dish.getToppings() != null) {
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
                logger.error("**********Service  VICTORY *****************");
            }


        }

    }
}
