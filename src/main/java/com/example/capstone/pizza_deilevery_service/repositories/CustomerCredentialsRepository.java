package com.example.capstone.pizza_deilevery_service.repositories;

import com.example.capstone.pizza_deilevery_service.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCredentialsRepository extends JpaRepository<CustomerEntity,Integer> {
}