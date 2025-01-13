package com.example.groceryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.groceryapp.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {}