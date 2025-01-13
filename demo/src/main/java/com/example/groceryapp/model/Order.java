package com.example.groceryapp.model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.groceryapp.repository.OrderItem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "orders") // Specify the new table name
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<OrderItem> orderItems = new ArrayList<>(); // Items in the order

    private String userId;

    private LocalDateTime createdAt;

    // Add a method to add an item to the order
    public void addItem(Long groceryItemId, int quantity) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getGroceryItemId().equals(groceryItemId)) {
                // If the item is already in the order, increase the quantity
                orderItem.setQuantity(orderItem.getQuantity() + quantity);
                return;
            }
        }
        // If the item is not already in the order, add it as a new item
        orderItems.add(new OrderItem(groceryItemId, quantity));
    }
}
