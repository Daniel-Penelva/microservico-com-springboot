package com.microservice.inventoryservice.repository;

import com.microservice.inventoryservice.model.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
