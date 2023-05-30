package com.example.capstone.pizza_delivery_service.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.time.LocalDate;
import java.util.List;

@Component
@SessionScope
@NoArgsConstructor
@Getter
@Setter
public class Customer{

    private Integer id;

    @Size(min=2, max=50)
    @NotEmpty(message = "Name is required")
    private String name;
    @Size(min=2, max=50)
    private String surname;
    @Size(min=2, max=20, message = "Mobile number must be less 20 digits")
    @NotEmpty
    @Pattern(regexp = "^[0-9]*$", message = "Phone number must only be digits")
    private String mobile;

//    @DateTimeFormat (pattern = "yyyy-mm-dd")
    @NotNull (message = "Date of birth is required")
    @Past(message = "Date of birth must be in past")
    private LocalDate DOB;
    @Size(min=5, max=50)
    @NotEmpty (message = "Email is required")
    @Email(message = "Please enter a valid e-mail address")
    private String email;
    @NotEmpty(message = "Home address is required")
    @Size(min=5, max=200, message = "Home address must be less 200 symbols")
    private String homeAddress;
    @NotEmpty(message = "Username is required")
    @Size(min=5, max=20, message = "Username must be less 20 symbols")
    private String username;

    @NotEmpty(message = "Password is required")
    @Size(min=5, max=20)
    @Pattern(regexp = "^(?=(.*[A-Z]){1,})(?=(.*[0-9]){1,})(?=(.*[!@#$%^&*()-__+.]){1,}).{8,}$",message = "Password should be 8 symbols and more, have 1 spec and 1 capital letter")
    private String password;

    private List<String> roles;

    public Customer(Integer id, String name, String surname, String mobile, LocalDate DOB, String email, String homeAddress, String username, String password, List<String> roles) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mobile = mobile;
        this.DOB = DOB;
        this.email = email;
        this.homeAddress = homeAddress;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}

