package com.example.capstone.pizza_delivery_service.mapper;

import com.example.capstone.pizza_delivery_service.entity.ToppingsEntity;
import com.example.capstone.pizza_delivery_service.model.Toppings;
import org.springframework.stereotype.Component;


@Component
public class ToppingsMapper {


  public Toppings mapToppingsEntityToModel (ToppingsEntity toppings){
        return new Toppings(toppings.getName(), toppings.getPrice());
    }

}
