package com.example.groceryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.groceryapp.model.GroceryItem;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> 
{



}
