package com.example.capstone.pizza_delivery_service.service;

import com.example.capstone.pizza_delivery_service.controller.Controller;
import com.example.capstone.pizza_delivery_service.entity.FoodTypesEntity;
import com.example.capstone.pizza_delivery_service.entity.OrdersEntity;
import com.example.capstone.pizza_delivery_service.mapper.CustomerMapper;
import com.example.capstone.pizza_delivery_service.mapper.FoodTypesMapper;
import com.example.capstone.pizza_delivery_service.model.Customer;
import com.example.capstone.pizza_delivery_service.model.FoodTypes;
import com.example.capstone.pizza_delivery_service.model.Toppings;
import com.example.capstone.pizza_delivery_service.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.List;

@Service
public class DatabaseService {
    Logger logger = LoggerFactory.getLogger(Controller.class);

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
       if (customersCredentialsRepository.findByUsername(username).isEmpty()){
            return false;}
           return true;
    }

    public void loadImages(){
       List<FoodTypesEntity> foodTypesList=foodTypesRepository.findAll();
        int id;
        try
        {
            logger.error("****************** testing filewriting ************");
            for (var foodtype:foodTypesList
             ) {
                byte[] b=foodtype.getImage();
                    id=foodtype.getId();

                    String fileName="src/main/resources/static/images/"+id+".jpg";

                     FileOutputStream fos = new FileOutputStream(new File(fileName));
                    fos.write(b);
                    fos.close();
            }
        }
            catch(Exception err)
            {
                err.printStackTrace();
            }


        }




}
