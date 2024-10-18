package com.jelwery.morri.Repository;

import com.jelwery.morri.Model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
}
