package com.example.capstone.pizza_delivery_service;

import com.example.capstone.pizza_delivery_service.entity.AuthGroupEntity;
import com.example.capstone.pizza_delivery_service.entity.CustomersCredentialsEntity;
import com.example.capstone.pizza_delivery_service.model.UserPrincipal;
import com.example.capstone.pizza_delivery_service.repositories.AuthGroupRepository;
import com.example.capstone.pizza_delivery_service.repositories.CustomersCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private  CustomersCredentialsRepository customersCredentialsRepository;
    @Autowired

    private  AuthGroupRepository authGroupRepository;


    public UserDetailsService() {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CustomersCredentialsEntity user = customersCredentialsRepository.findByUsername(username).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("cannot find username: " + username);

        }
        List<AuthGroupEntity> authGroups = authGroupRepository.findAllBycustomerscredentialsid(user.getId());
        return new UserPrincipal(user, authGroups);
    }
}
