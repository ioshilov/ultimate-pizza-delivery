package com.example.capstone.pizza_delivery_service.service;

import com.example.capstone.pizza_delivery_service.security.UserPrincipal;
import com.example.capstone.pizza_delivery_service.controller.Controller;
import com.example.capstone.pizza_delivery_service.entity.*;
import com.example.capstone.pizza_delivery_service.mapper.FoodTypesMapper;
import com.example.capstone.pizza_delivery_service.mapper.ToppingsMapper;
import com.example.capstone.pizza_delivery_service.model.*;
import com.example.capstone.pizza_delivery_service.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    Logger logger = LoggerFactory.getLogger(Controller.class);

    private final CustomersCredentialsRepository customersCredentialsRepository;

    private final CustomersRepository customersRepository;

    private final OrdersRepository ordersRepository;

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderCartRepository orderCartRepository;

    private final DishesRepository dishesRepository;

    private final ToppingsRepository toppingsRepository;

    private final PaymentRepository paymentRepository;

    private final FoodTypesRepository foodTypesRepository;

    private final DatabaseService databaseService;

    @Autowired
    public CustomerService(CustomersCredentialsRepository customersCredentialsRepository,
                           CustomersRepository customersRepository,
                           OrdersRepository ordersRepository,
                           OrderDetailsRepository orderDetailsRepository,
                           OrderCartRepository orderCartRepository,
                           DishesRepository dishesRepository,
                           ToppingsRepository toppingsRepository,
                           PaymentRepository paymentRepository,
                           FoodTypesRepository foodTypesRepository,
                           DatabaseService databaseService) {
        this.customersCredentialsRepository = customersCredentialsRepository;
        this.customersRepository = customersRepository;
        this.ordersRepository = ordersRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderCartRepository = orderCartRepository;
        this.dishesRepository = dishesRepository;
        this.toppingsRepository = toppingsRepository;
        this.paymentRepository = paymentRepository;
        this.foodTypesRepository = foodTypesRepository;
        this.databaseService = databaseService;
    }

    public void createOrder(OrderCart orderCart, OrderDetails orderDetails, UserPrincipal user, String payment) {
        OrdersEntity ordersEntity = new OrdersEntity();
        ordersEntity.setLocalDate(LocalDate.now());
        ordersEntity.setSum(orderCart.getDishesList().stream().map(Dishes::getSum).reduce(BigDecimal.ZERO, BigDecimal::add));

        if (user != null) {
            ordersEntity.setCustomerid(customersCredentialsRepository.findByUsername(user.getUsername()).get().getCustomerid());
        }
        ordersRepository.save(ordersEntity);
        Integer ordersEntityID = ordersEntity.getId();
        logger.info("ORDER ID =" + ordersEntityID.toString());
        OrderCartEntity orderCartEntity = fillOrderCartWithDishesAndSave(orderCart);

        OrderDetailsEntity orderDetailsEntity = new OrderDetailsEntity();
        orderDetailsEntity.setDeliveryName(orderDetails.getName());
        orderDetailsEntity.setDeliveryMobile(orderDetails.getMobile());
        orderDetailsEntity.setDeliveryPrice(BigDecimal.valueOf(1500));
        orderDetailsEntity.setDeliveryAddress(orderDetails.getAddress());
        orderDetailsEntity.setDeliveryComments(orderDetails.getComments());
        orderDetailsEntity.setOrderCartEntity(orderCartEntity);
        orderDetailsEntity.setOrdersEntity(ordersEntity);
        orderDetailsRepository.save(orderDetailsEntity);

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setOrdersEntity(ordersEntity);
        paymentEntity.setPaymentmethod(payment);
        paymentRepository.save(paymentEntity);

        logger.warn("**********Service. Order details saved *****************");
    }

    public OrderCartEntity fillOrderCartWithDishesAndSave(OrderCart orderCart) {
        List<ToppingsEntity> toppingsEntityList = toppingsRepository.findAll();
        OrderCartEntity orderCartEntity = new OrderCartEntity();

        for (var dish : orderCart.getDishesList()) {
            DishesEntity dishesEntity = new DishesEntity();
            String foodName = dish.getFoodType().getName();
            FoodTypesEntity foodTypesEntity = foodTypesRepository.findByName(foodName);
            dishesEntity.setFoodTypesEntity(foodTypesEntity);
            dish.getToppings().forEach(x -> {
                ToppingsEntity toppingsEntity = toppingsEntityList.stream().filter(topping -> topping.getName().equals(x.getName())).findFirst().get();
                dishesEntity.addToppings(toppingsEntity);
            });

            dishesRepository.save(dishesEntity);
            orderCartEntity.addDishes(dishesEntity);
            logger.warn("**********Service  STAGE  add dish " + dishesEntity + " to cart *****************");
//            }


        }
        orderCartRepository.save(orderCartEntity);
        logger.info("**********Service  VICTORY *****************");
        return orderCartEntity;


    }

    public Dishes createDishes(Integer ID, String[] toppings) {
        ToppingsMapper toppingsMapper = new ToppingsMapper();
        FoodTypesMapper foodTypesMapper = new FoodTypesMapper();
        List<Toppings> toppingsList = new ArrayList<>();
        if (toppings != null) {
            for (String topping : toppings) {
                toppingsList.add(toppingsMapper.mapToppingsEntityToModel(toppingsRepository.findByName(topping)));
            }
        }
        Dishes dishes = new Dishes(foodTypesMapper.mapFoodTypesEntityToModel(foodTypesRepository.findById(ID).get()));
        dishes.setToppings(toppingsList);
        return dishes;
    }

    public CustomerEntity registerNewCustomer(Customer customer) {

        CustomersCredentialsEntity credentials = new CustomersCredentialsEntity();
        CustomerEntity customerEntity = new CustomerEntity();
        AuthGroupEntity authGroupEntity = new AuthGroupEntity();

        authGroupEntity.setAuthgroup("CUSTOMER");
        List<AuthGroupEntity> authGroupEntityList = new ArrayList<>();
        authGroupEntityList.add(authGroupEntity);

//        authGroupEntity.setCustomersCredentialsEntity(credentials);

        credentials.setUsername(customer.getUsername());
        credentials.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));
        credentials.setAuthGroupEntityList(authGroupEntityList);
        credentials.setCustomerEntity(customerEntity);


        customerEntity.setName(customer.getName());
        customerEntity.setSurname(customer.getSurname());
        customerEntity.setMobile(customer.getMobile());
        customerEntity.setHomeAddress(customer.getHomeAddress());
        customerEntity.setDOB(customer.getDOB());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setCustomersCredentialsEntity(credentials);



        logger.warn("********************Service. Customer to DB  " + customerEntity + "***************");
        return customersRepository.save(customerEntity);

    }
}


