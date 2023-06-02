package com.example.capstone.pizza_delivery_service;


import com.example.capstone.pizza_delivery_service.entity.DishesEntity;
import com.example.capstone.pizza_delivery_service.entity.FoodTypesEntity;
import com.example.capstone.pizza_delivery_service.entity.OrderCartEntity;
import com.example.capstone.pizza_delivery_service.entity.ToppingsEntity;
import com.example.capstone.pizza_delivery_service.mapper.ToppingsMapper;
import com.example.capstone.pizza_delivery_service.model.Dishes;
import com.example.capstone.pizza_delivery_service.model.FoodTypes;
import com.example.capstone.pizza_delivery_service.model.OrderCart;
import com.example.capstone.pizza_delivery_service.model.Toppings;
import com.example.capstone.pizza_delivery_service.repositories.DishesRepository;
import com.example.capstone.pizza_delivery_service.repositories.FoodTypesRepository;
import com.example.capstone.pizza_delivery_service.repositories.OrderCartRepository;
import com.example.capstone.pizza_delivery_service.repositories.ToppingsRepository;
import com.example.capstone.pizza_delivery_service.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FillOrderCartAndSaveUnitTest {
    @Mock
    private FoodTypesRepository foodTypesRepository;
    @Mock
    private ToppingsRepository toppingsRepository;
    @Mock
    private DishesRepository dishesRepository;
    @Mock
    private OrderCartRepository orderCartRepository;

    @InjectMocks
    private CustomerService customerService;
    @InjectMocks
    private ToppingsMapper toppingsMapper;


    @Test
    public void testFillOrderCartAndSave() {

        OrderCart orderCart = new OrderCart();
        OrderCartEntity orderCartEntity = new OrderCartEntity();

        ToppingsEntity toppingsJalapeno = new ToppingsEntity(1, "jalapeno", BigDecimal.valueOf(100));
        ToppingsEntity toppingsOlives = new ToppingsEntity(2, "olives", BigDecimal.valueOf(200));

        Toppings toppingsOne = toppingsMapper.mapToppingsEntityToModel(toppingsJalapeno);
        Toppings toppingsTwo = toppingsMapper.mapToppingsEntityToModel(toppingsOlives);

        List<Toppings> toppingsListOne = new ArrayList<>();
        List<Toppings> toppingsListTwo = new ArrayList<>();

        toppingsListOne.add(toppingsOne);
        toppingsListOne.add(toppingsTwo);
        toppingsListTwo.add(toppingsOne);

        FoodTypesEntity foodTypesEntityOne = new FoodTypesEntity(1, "Pizza Diablo", BigDecimal.valueOf(1500), "test description one");
        FoodTypesEntity foodTypesEntityTwo = new FoodTypesEntity(2, "Pizza Margarita", BigDecimal.valueOf(2000), "test description two");

        Dishes testDishesOne = new Dishes(new FoodTypes(1, "Pizza Diablo", BigDecimal.valueOf(1500), "test description one"));
        Dishes testDishesTwo = new Dishes(new FoodTypes(2, "Pizza Margarita", BigDecimal.valueOf(2000), "test description two"));

        testDishesOne.setToppings(toppingsListOne);
        testDishesTwo.setToppings(toppingsListTwo);

        orderCart.addDishes(testDishesOne);
        orderCart.addDishes(testDishesTwo);

        List<ToppingsEntity> toppingsEntityList = new ArrayList<>();
        toppingsEntityList.add(toppingsOlives);
        toppingsEntityList.add(toppingsJalapeno);

        List<ToppingsEntity> toppingsEntityListTwo = new ArrayList<>();
        toppingsEntityListTwo.add(toppingsJalapeno);

        DishesEntity dishesEntityOne = new DishesEntity(1, foodTypesEntityOne, toppingsEntityList);
        DishesEntity dishesEntityTwo = new DishesEntity(2, foodTypesEntityTwo, toppingsEntityListTwo);

        orderCartEntity.addDishes(dishesEntityOne);
        orderCartEntity.addDishes(dishesEntityTwo);
        orderCartEntity.setOrderCartID(1);

        when(foodTypesRepository.findByName(any())).thenReturn(foodTypesEntityOne).thenReturn(foodTypesEntityTwo);

        when(toppingsRepository.findAll()).thenReturn(toppingsEntityList);

        when(dishesRepository.save(any())).thenReturn(dishesEntityOne).thenReturn(dishesEntityTwo);

        when(orderCartRepository.save(any())).thenReturn(orderCartEntity);

        customerService.fillOrderCartWithDishesAndSave(orderCart);

        assertNotNull(orderCartEntity);
        assertEquals("Pizza Diablo", orderCartEntity.getDishesEntityList().get(0).getFoodTypesEntity().getName());
        assertEquals(1, orderCartEntity.getOrderCartID());
        assertTrue(orderCartEntity.getDishesEntityList().get(0).getToppingsEntitySet().contains(toppingsOlives));
        assertTrue(orderCartEntity.getDishesEntityList().get(1).getToppingsEntitySet().contains(toppingsJalapeno));
    }
}
