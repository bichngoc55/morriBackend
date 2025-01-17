package com.jelwery.morri.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.InventoryProduct;

public interface InventoryProductRepository extends MongoRepository<InventoryProduct, String> {
    

}
