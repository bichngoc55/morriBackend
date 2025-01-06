package com.jelwery.morri.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.CartItem;

public interface CartItemRepository extends MongoRepository<CartItem,String> {

}
