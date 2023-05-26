package com.example.capstone.pizza_delivery_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Order {

    private Integer Id  ;
    private Integer customerID;
    private BigDecimal sum;

    private LocalDate date;

    public Order(BigDecimal sum, LocalDate date) {
        this.sum = sum;
        this.date = date;
    }
}
