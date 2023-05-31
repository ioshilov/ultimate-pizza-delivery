package com.example.capstone.pizza_delivery_service.repositories;

import com.example.capstone.pizza_delivery_service.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<OrdersEntity,Integer> {

    List<OrdersEntity> findAllBycustomerid(Integer id);

   

}
