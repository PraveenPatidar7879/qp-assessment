package com.example.groceryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.groceryapp.model.User;

public interface UserRepository extends JpaRepository<User, String> 
{

}