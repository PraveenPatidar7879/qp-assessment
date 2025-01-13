package com.example.groceryapp.service;
import com.example.groceryapp.model.GroceryItem;
import com.example.groceryapp.repository.GroceryItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroceryItemService {
    private final GroceryItemRepository repository;

    public GroceryItemService(GroceryItemRepository repository) {
        this.repository = repository;
    }

    public GroceryItem addGroceryItem(GroceryItem item) {
        return repository.save(item);
    }

    public List<GroceryItem> getAllGroceryItems() {
        return repository.findAll();
    }

    public void deleteGroceryItem(Long id) {
        repository.deleteById(id);
    }

    public GroceryItem updateGroceryItem(Long id, GroceryItem updatedItem) {
        return repository.findById(id).map(item -> {
            item.setName(updatedItem.getName());
            item.setPrice(updatedItem.getPrice());
            item.setInventory(updatedItem.getInventory());
            return repository.save(item);
        }).orElseThrow(() -> new RuntimeException("Item not found"));
    }
}
