package com.example.capstone.pizza_delivery_service.repositories;

import com.example.capstone.pizza_delivery_service.entity.CustomersCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomersCredentialsRepository extends JpaRepository<CustomersCredentialsEntity,Integer> {

    Optional<CustomersCredentialsEntity> findByUsername(String user);
}