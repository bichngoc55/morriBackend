package com.jelwery.morri.Repository;

import com.jelwery.morri.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
