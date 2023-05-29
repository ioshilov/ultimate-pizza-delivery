package com.example.capstone.pizza_delivery_service.model;


import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;

@Data
@Component
@SessionScope
public class FoodTypes {
    private Integer id;
    private String Name;
    private BigDecimal Price;
    private String Description;

    public FoodTypes(Integer id, String name, BigDecimal price, String description) {
        this.id = id;
        Name = name;
        Price = price;
        Description = description;
    }
}
