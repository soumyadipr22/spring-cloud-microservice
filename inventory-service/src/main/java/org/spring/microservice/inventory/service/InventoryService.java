package org.spring.microservice.inventory.service;

import org.spring.microservice.inventory.entity.Inventory;
import org.spring.microservice.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;
	
	public Inventory getInventory(String skuCode) throws Exception {
		log.info("Inside getInventory method of InventoryService");
		Inventory inventory = inventoryRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new RuntimeException("Cannot Find Product by sku code : " + skuCode));
		return inventory;
	}
	
}
