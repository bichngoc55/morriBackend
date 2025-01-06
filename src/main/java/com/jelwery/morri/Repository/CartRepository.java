package com.jelwery.morri.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.Cart;

public interface CartRepository extends MongoRepository<Cart, String>{
    Cart findByUserId(String userId);

}
