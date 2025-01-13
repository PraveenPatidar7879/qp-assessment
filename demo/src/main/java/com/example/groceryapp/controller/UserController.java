package com.example.groceryapp.controller;

import com.example.groceryapp.Exception.OutOfStockException;
import com.example.groceryapp.model.GroceryItem;
import com.example.groceryapp.model.Order;
import com.example.groceryapp.service.GroceryItemService;
import com.example.groceryapp.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final GroceryItemService groceryService;
    private final OrderService orderService;

    public UserController(GroceryItemService groceryService, OrderService orderService) {
        this.groceryService = groceryService;
        this.orderService = orderService;
    }

    // Get all grocery items
    @GetMapping("/items")
    public ResponseEntity<List<GroceryItem>> getAllItems() {
        List<GroceryItem> items = groceryService.getAllGroceryItems();
        if (items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(items); // 204 No Content if no items
        }
        return ResponseEntity.ok(items); // 200 OK if items exist
    }

    // Place an order
   @PostMapping("/order")
public ResponseEntity<?> placeOrder(@RequestBody Order order) {
    try {
        Order placedOrder = orderService.placeOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(placedOrder); // 201 Created for successful order placement
    } catch (OutOfStockException e) {
        // Return a detailed message for out-of-stock items with 400 status
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", "Out of Stock",
                        "message", e.getMessage()
                ));
    } catch (Exception e) {
        // General exception handler for unexpected errors
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", "Internal Server Error",
                        "message", e.getMessage()
                ));
    }
}


    // Exception handler for handling specific exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("An error occurred: " + e.getMessage());
    }
}
