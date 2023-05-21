package com.example.capstone.pizza_deilevery_service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Customer{

    @JsonCreator
    public Customer(@JsonProperty("id")final Integer id,
                    @JsonProperty("name") String name,
                    @JsonProperty("surname") String surname,
                    @JsonProperty("mobile") String mobile,
                    @JsonProperty("DOB") LocalDate DOB,
                    @JsonProperty("email") String email,
                    @JsonProperty("homeAddress") String homeAddress) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mobile = mobile;
        this.DOB = DOB;
        this.email = email;
        this.homeAddress = homeAddress;
    }

    private Integer id;
    private String name;
    private String surname;

    private String mobile;

    private LocalDate DOB;

    private String email;

    private String homeAddress;

}

