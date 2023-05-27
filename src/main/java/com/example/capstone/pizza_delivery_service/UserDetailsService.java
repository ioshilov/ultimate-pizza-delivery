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
import java.util.Optional;
import java.util.stream.Collectors;


@Service
    public class UserDetailsService  implements org.springframework.security.core.userdetails.UserDetailsService {


        private final CustomersCredentialsRepository customersCredentialsRepository;

        private final AuthGroupRepository authGroupRepository;


    public UserDetailsService(CustomersCredentialsRepository customersCredentialsRepository, AuthGroupRepository authGroupRepository) {
        this.customersCredentialsRepository = customersCredentialsRepository;
        this.authGroupRepository = authGroupRepository;
    }

    @Override
        public UserDetails loadUserByUsername(String username_test) throws UsernameNotFoundException {

String username=username_test;
            List<CustomersCredentialsEntity> list=customersCredentialsRepository.findAll();

            Optional<CustomersCredentialsEntity> user =list.stream().filter(x->x.getUsername().equals(username)).findFirst();

//            Optional<CustomersCredentialsEntity> user = this.customersCredentialsRepository.findByUsername(username);

            if(user.isEmpty()){
                throw new UsernameNotFoundException("cannot find username: " + username);
            }
            List<AuthGroupEntity> listAuth=authGroupRepository.findAll();
            List<AuthGroupEntity> authGroups =listAuth.stream().filter(x->x.getUsername().equals(username)).toList();


//            List<AuthGroupEntity> authGroups = this.authGroupRepository.findAllByusername(username);
            return new UserPrincipal(user.get(), authGroups);
        }
    }
