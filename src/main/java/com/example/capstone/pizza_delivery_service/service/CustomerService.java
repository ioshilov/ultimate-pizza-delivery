package com.example.capstone.pizza_delivery_service.service;

import com.example.capstone.pizza_delivery_service.controller.Controller;
import com.example.capstone.pizza_delivery_service.entity.*;
import com.example.capstone.pizza_delivery_service.model.*;
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
    @Autowired
    private CustomersCredentialsRepository customersCredentialsRepository;
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
    private PaymentRepository paymentRepository;

    @Autowired

    private FoodTypesRepository foodTypesRepository;


    public CustomerService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public List<Customer> getAllCustomers() {

        return customersRepository.findAll().stream().map(x -> new Customer(x.getId(),
                x.getName(),
                x.getSurname(),
                x.getMobile(),
                x.getDOB(),
                x.getEmail(),
                x.getHomeAddress(),
                x.getCustomersCredentialsEntity().getUsername(),
                x.getCustomersCredentialsEntity().getPassword(),
                x.getCustomersCredentialsEntity().getAuthGroupEntityList().stream().map(AuthGroupEntity::getAuthgroup).collect(Collectors.toList()))).collect(Collectors.toList());
    }

    public Customer getAllOrders() {
        List<OrdersEntity> ordersEntityList = ordersRepository.findAll();
//ordersEntityList.stream().map(x->x.getOrderDetailsEntity().getOrderCartEntity().)

        return null;

    }

    public void createOrder(OrderCart orderCart, OrderDetails orderDetails, UserPrincipal user,String payment) {
        OrdersEntity ordersEntity = new OrdersEntity();
        ordersEntity.setLocalDate(LocalDate.now());
        ordersEntity.setSum(orderCart.getDishesList().stream().map(Dishes::getSum).reduce(BigDecimal.ZERO, BigDecimal::add));

        if(user!=null){ordersEntity.setCustomerid(customersCredentialsRepository.findByUsername(user.getUsername()).getCustomerid());}
        ordersRepository.save(ordersEntity);
        Integer ordersEntityID = ordersEntity.getId();
        logger.info("ORDER ID =" + ordersEntityID.toString());
        logger.info("**********Service  Before Cart fill *****************");
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

        PaymentEntity paymentEntity=new PaymentEntity();
        paymentEntity.setOrdersEntity(ordersEntity);
        paymentEntity.setPaymentmethod(payment);
        paymentRepository.save(paymentEntity);


        logger.info("**********Service  VICTORY Order details saved *****************");

    }

    public OrderCartEntity fillOrderCartWithDishesAndSave(OrderCart orderCart) {
        List<ToppingsEntity> toppingsEntityList = toppingsRepository.findAll();
        logger.info("**********Service  STAGE  1 *****************");
        OrderCartEntity orderCartEntity = new OrderCartEntity();

        for (var dish : orderCart.getDishesList()
        ) {

            logger.info("**********Service  STAGE  2 *****************");
            DishesEntity dishesEntity = new DishesEntity();
            String foodname = dish.getFoodType().getName();
            FoodTypesEntity foodTypesEntity = foodTypesRepository.findByName(foodname);
            dishesEntity.setFoodTypesEntity(foodTypesEntity);
            logger.warn("**********" + foodTypesRepository.findByName(dish.getFoodType().getName()).getName() + " *****************");
            logger.info("**********Service  STAGE  3 *****************");
            dish.getToppings().forEach(x -> {
                        logger.info("**********Service  STAGE  foreach 1 *****************");
                        ToppingsEntity toppingsEntity = toppingsEntityList.stream().filter(topping -> topping.getName().equals(x.getName())).findFirst().get();
                        logger.warn(toppingsEntity.getName());
                        logger.info("**********Service  STAGE  foreach 2 *****************");

                        dishesEntity.addToppings(toppingsEntity);
                        logger.info("**********Service  STAGE  foreach 3 *****************");
                        logger.warn(dishesEntity.toString());
                    }
            );
            logger.info("**********Service  STAGE  4 *****************");
            dishesRepository.save(dishesEntity);

            logger.info("**********Service  STAGE  4 Dishes Saved *****************");
            orderCartEntity.addDishes(dishesEntity);
            logger.warn("**********Service  STAGE  4 add dish " + dishesEntity.toString() + " to cart *****************");
//            }


        }
        orderCartRepository.save(orderCartEntity);
        logger.info("**********Service  VICTORY *****************");
        return orderCartEntity;


    }
}
