package org.spring.microservice.inventory.controller;

import org.spring.microservice.inventory.entity.Inventory;
import org.spring.microservice.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;

    @GetMapping("/{skuCode}")
    public Boolean isInStock(@PathVariable String skuCode) {
    	log.info("Inside isInStock method of InventoryController");
        log.info("Checking stock for product with skucode : " + skuCode);
        Inventory inventory = null;
        try {
        	inventory = inventoryService.getInventory(skuCode);
		} catch (Exception e) {
			log.warn(e.getMessage());
			return false;
		}
        
        return inventory.getStock() > 0;
    }
}
