package com.example.capstone.pizza_delivery_service.repositories;

import com.example.capstone.pizza_delivery_service.entity.AuthGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthGroupRepository extends JpaRepository<AuthGroupEntity,Integer> {

     List<AuthGroupEntity> findAllByid(Integer id);



}
