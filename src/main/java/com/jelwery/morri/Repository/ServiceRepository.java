package com.jelwery.morri.Repository;

import com.jelwery.morri.Model.Service;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServiceRepository extends MongoRepository<Service, String> {
}
