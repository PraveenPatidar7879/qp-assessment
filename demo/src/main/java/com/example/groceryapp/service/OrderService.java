package com.example.groceryapp.service;

import com.example.groceryapp.Exception.OutOfStockException;
import com.example.groceryapp.model.GroceryItem;
import com.example.groceryapp.model.Order;
import com.example.groceryapp.repository.GroceryItemRepository;
import com.example.groceryapp.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final GroceryItemRepository groceryItemRepository;

    public OrderService(OrderRepository orderRepository, GroceryItemRepository groceryItemRepository) {
        this.orderRepository = orderRepository;
        this.groceryItemRepository = groceryItemRepository;
    }

    public Order placeOrder(Order order) {
    List<String> outOfStockItems = new ArrayList<>(); // Collect out-of-stock item names

    order.getOrderItems().forEach(orderItem -> {
        // Fetch the grocery item from the inventory
        GroceryItem inventoryItem = groceryItemRepository.findById(orderItem.getGroceryItemId())
                .orElseThrow(() -> new RuntimeException("Item with ID " + orderItem.getGroceryItemId() + " not found in inventory"));

        // Check if enough inventory is available
        if (inventoryItem.getInventory() < orderItem.getQuantity()) {
            outOfStockItems.add(inventoryItem.getName()); // Add item name to out-of-stock list
        }
    });

    if (!outOfStockItems.isEmpty()) {
        // Throw a custom exception with out-of-stock item details
        throw new OutOfStockException("The following items are out of stock: " + String.join(", ", outOfStockItems));
    }

    // Deduct inventory for the items
    order.getOrderItems().forEach(orderItem -> {
        GroceryItem inventoryItem = groceryItemRepository.findById(orderItem.getGroceryItemId()).get();
        inventoryItem.setInventory(inventoryItem.getInventory() - orderItem.getQuantity());
        groceryItemRepository.save(inventoryItem); // Update inventory
    });

    order.setCreatedAt(LocalDateTime.now());
    return orderRepository.save(order); // Save the order to the database
}


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
