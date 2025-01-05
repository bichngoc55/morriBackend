package com.jelwery.morri.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.OrderDetail;

public interface OrderDetailRepository extends MongoRepository<OrderDetail,String>{

}
