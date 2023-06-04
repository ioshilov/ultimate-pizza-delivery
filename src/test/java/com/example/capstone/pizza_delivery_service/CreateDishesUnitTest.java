package com.example.capstone.pizza_delivery_service;

import com.example.capstone.pizza_delivery_service.entity.FoodTypesEntity;
import com.example.capstone.pizza_delivery_service.entity.ToppingsEntity;
import com.example.capstone.pizza_delivery_service.model.Dishes;
import com.example.capstone.pizza_delivery_service.repositories.FoodTypesRepository;
import com.example.capstone.pizza_delivery_service.repositories.ToppingsRepository;
import com.example.capstone.pizza_delivery_service.service.ShoppingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateDishesUnitTest {
    @Mock
    private FoodTypesRepository foodTypesRepository;
    @Mock
    private ToppingsRepository toppingsRepository;

    @InjectMocks
    private ShoppingService shoppingService;

    @Test
    public void testCreateDishes() {

        // Create a contact
        Integer id = 1;
        String[] toppingsList = {"jalapeno", "olives"};
        ToppingsEntity toppingsJalapeno = new ToppingsEntity();
        toppingsJalapeno.setName("jalapeno");
        toppingsJalapeno.setPrice(BigDecimal.valueOf(100));

        ToppingsEntity toppingsOlives = new ToppingsEntity();
        toppingsOlives.setName("olives");
        toppingsOlives.setPrice(BigDecimal.valueOf(200));

        FoodTypesEntity pizzaEntity = new FoodTypesEntity();

        pizzaEntity.setName("Pizza Diablo");
        pizzaEntity.setPrice(BigDecimal.valueOf(1500));
        pizzaEntity.setDescription("just default pizza");

        when(toppingsRepository.findByName(any())).thenReturn(toppingsJalapeno).thenReturn(toppingsOlives);

        when(foodTypesRepository.findById(id)).thenReturn(Optional.of(pizzaEntity));

        Dishes newDishes = shoppingService.createDishes(id, toppingsList);

        assertNotNull(newDishes);
        assertEquals("Pizza Diablo", newDishes.getFoodType().getName());
        assertEquals("jalapeno", newDishes.getToppings().get(0).getName());
        assertEquals("olives", newDishes.getToppings().get(1).getName());
    }
}
