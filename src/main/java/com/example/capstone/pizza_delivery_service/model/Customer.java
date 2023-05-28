package com.example.capstone.pizza_delivery_service.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDate;

@Component
@SessionScope
@NoArgsConstructor
@Getter
@Setter
public class Customer{

      private Integer id;
    private String name;
    private String surname;

    private String mobile;

    private LocalDate DOB;

    private String email;

    private String homeAddress;

    private String username;

    private String password;



}

