package com.example.groceryapp.controller;

import com.example.groceryapp.dto.InventoryLevelDTO;
import com.example.groceryapp.model.GroceryItem;
import com.example.groceryapp.repository.GroceryItemRepository;
import com.example.groceryapp.service.GroceryItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final GroceryItemService service;
    private final GroceryItemRepository groceryItemRepository;

    public AdminController(GroceryItemService service, GroceryItemRepository groceryItemRepository) {
        this.service = service;
        this.groceryItemRepository = groceryItemRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<GroceryItem> addGroceryItem(@RequestBody GroceryItem item) {
        GroceryItem addedItem = service.addGroceryItem(item);
        return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    @GetMapping("/items")
    public ResponseEntity<List<GroceryItem>> getAllItems() {
        List<GroceryItem> items = service.getAllGroceryItems();
        return ResponseEntity.ok(items);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGroceryItem(@PathVariable Long id) {
        try {
            service.deleteGroceryItem(id);
            return ResponseEntity.ok("Item deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GroceryItem> updateGroceryItem(@PathVariable Long id, @RequestBody GroceryItem item) {
        try {
            GroceryItem updatedItem = service.updateGroceryItem(id, item);
            return ResponseEntity.ok(updatedItem);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // View inventory levels
    @GetMapping("/inventory")
    public ResponseEntity<List<InventoryLevelDTO>> viewInventoryLevels() {


        List<InventoryLevelDTO> items = groceryItemRepository.findAll().stream()
        .map(item-> new InventoryLevelDTO(item.getId(),item.getName(),item.getInventory()))
        .collect(Collectors.toList());
        return ResponseEntity.ok(items);
    }
    
    // Update inventory level for a specific item
    @PutMapping("/inventory/{id}")
    public ResponseEntity<GroceryItem> updateInventoryLevel(@PathVariable Long id, @RequestParam int inventory) {
        try {
            GroceryItem item = groceryItemRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Item not found with ID: " + id));
            item.setInventory(inventory);
            GroceryItem updatedItem = groceryItemRepository.save(item);
            return ResponseEntity.ok(updatedItem);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Global exception handler for this controller
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
    }
}
