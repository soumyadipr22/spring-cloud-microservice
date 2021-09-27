package org.spring.microservice.inventory.repository;

import java.util.Optional;

import org.spring.microservice.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	Optional<Inventory> findBySkuCode(String skuCode);
}
