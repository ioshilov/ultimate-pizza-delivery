package com.example.capstone.pizza_delivery_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@NoArgsConstructor
@Component
@SessionScope
@Data
public class OrderDetails {

    private String name;
    private String mobile;
    private String address;
    private String comments;

}
