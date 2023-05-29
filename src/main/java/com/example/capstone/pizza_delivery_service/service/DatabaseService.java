package com.example.capstone.pizza_delivery_service.service;

import com.example.capstone.pizza_delivery_service.entity.OrdersEntity;
import com.example.capstone.pizza_delivery_service.mapper.CustomerMapper;
import com.example.capstone.pizza_delivery_service.mapper.FoodTypesMapper;
import com.example.capstone.pizza_delivery_service.model.Customer;
import com.example.capstone.pizza_delivery_service.model.FoodTypes;
import com.example.capstone.pizza_delivery_service.model.Toppings;
import com.example.capstone.pizza_delivery_service.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import java.util.List;

@Service
public class DatabaseService {

    @Autowired
    private  CustomersRepository customersRepository;
    @Autowired
    private CustomersCredentialsRepository customersCredentialsRepository;
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

    @Autowired
    private FoodTypesMapper foodTypesMapper;

    @Autowired
    private CustomerMapper customerMapper;

    public List<Customer> getAllCustomers() {

        return customersRepository.findAll().stream().map(x -> customerMapper.mapCustomerEntityToModel(x)).toList();
    }

    public List<Toppings> getAllToppings(){
        return toppingsRepository.findAll().stream().map(x->new Toppings(x.getName(),x.getPrice())).toList();
    }

    public List<FoodTypes> getAllFoodTypes(){
        return foodTypesRepository.findAll().stream().map(x->foodTypesMapper.mapFoodTypesEntityToModel(x)).toList();
    }

    public Customer findCustomerById(Integer id){
        return customerMapper.mapCustomerEntityToModel(customersRepository.findById(id).get());
    }

    public List<OrdersEntity> getAllOrders (){
        return ordersRepository.findAll();
    }

    public Boolean checkNewCustomerUsername (String username){
       if (customersCredentialsRepository.findByUsername(username).isEmpty()){return false;}
           return true;
    }


}
