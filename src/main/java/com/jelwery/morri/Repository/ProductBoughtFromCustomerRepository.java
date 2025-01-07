package com.jelwery.morri.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.ProductBoughtFromCustomer;

public interface ProductBoughtFromCustomerRepository extends MongoRepository<ProductBoughtFromCustomer,String> {

}
