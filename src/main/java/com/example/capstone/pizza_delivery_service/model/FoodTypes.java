package com.example.capstone.pizza_delivery_service.model;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodTypes {

    private String Name;
    private BigDecimal Price;
    private String Description;

    public FoodTypes(String name, BigDecimal price, String description) {
        Name = name;
        Price = price;
        Description = description;
    }
}
