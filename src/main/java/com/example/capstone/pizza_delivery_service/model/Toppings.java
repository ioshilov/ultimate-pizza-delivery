package com.example.capstone.pizza_delivery_service.model;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class Toppings {

   private String Name;
   private BigDecimal Price;


    public Toppings(String name, BigDecimal price) {
        Name = name;
        Price = price;
    }
}
