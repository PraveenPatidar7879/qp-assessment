package com.example.groceryapp.repository;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderItem {
    private Long groceryItemId; // ID of the grocery item
    private int quantity; // Quantity of the item in the order
}
