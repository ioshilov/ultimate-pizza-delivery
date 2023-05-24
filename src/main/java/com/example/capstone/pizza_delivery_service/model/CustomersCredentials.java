package com.example.capstone.pizza_delivery_service.model;


import com.example.capstone.pizza_delivery_service.entity.CustomerEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CustomersCredentials {


    private Integer id;
    private String login;

    private String password;

    private String role;

    public CustomersCredentials(Integer id, String login, String password, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
