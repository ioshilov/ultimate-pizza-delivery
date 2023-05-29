package com.example.capstone.pizza_delivery_service;

import com.example.capstone.pizza_delivery_service.controller.Controller;
import com.example.capstone.pizza_delivery_service.entity.AuthGroupEntity;
import com.example.capstone.pizza_delivery_service.entity.CustomersCredentialsEntity;
import com.example.capstone.pizza_delivery_service.model.UserPrincipal;
import com.example.capstone.pizza_delivery_service.repositories.AuthGroupRepository;
import com.example.capstone.pizza_delivery_service.repositories.CustomersCredentialsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    Logger logger = LoggerFactory.getLogger(Controller.class);
    private final CustomersCredentialsRepository customersCredentialsRepository;

    private final AuthGroupRepository authGroupRepository;


    public UserDetailsService(CustomersCredentialsRepository customersCredentialsRepository, AuthGroupRepository authGroupRepository) {
        this.customersCredentialsRepository = customersCredentialsRepository;
        this.authGroupRepository = authGroupRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            CustomersCredentialsEntity user = customersCredentialsRepository.findByUsername(username);

        if (user==null) {
            throw new UsernameNotFoundException("cannot find username: " + username);

        }
            List<AuthGroupEntity> authGroups = authGroupRepository.findAllBycustomerscredentialsid(user.getId());
        return new UserPrincipal(user, authGroups);
    }
}
