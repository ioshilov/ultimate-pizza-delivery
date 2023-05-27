package com.example.capstone.pizza_delivery_service.model;


import lombok.Data;

@Data
public class CustomersCredentials {


    private Integer id;
    private String login;

    private String password;



    public CustomersCredentials(Integer id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }
}
