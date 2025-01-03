package com.jelwery.morri.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.Supplier;

public interface SupplierRespository extends MongoRepository<Supplier,String> {
     Optional<Supplier> findBySupplierPhone (String supplierPhone);
}
