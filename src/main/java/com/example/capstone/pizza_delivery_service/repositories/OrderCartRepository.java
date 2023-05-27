package com.example.capstone.pizza_delivery_service.repositories;

import com.example.capstone.pizza_delivery_service.entity.OrderCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCartRepository extends JpaRepository<OrderCartEntity,Integer> {
}
