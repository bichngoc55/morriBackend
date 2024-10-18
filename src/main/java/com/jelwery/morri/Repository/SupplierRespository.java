package com.jelwery.morri.Repository;

import com.jelwery.morri.Model.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupplierRespository extends MongoRepository<Supplier,String> {
}
