package com.jelwery.morri.Repository;

import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Model.STATUS;
import com.jelwery.morri.Model.TYPE;


import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
     Optional<Product> findByName(String name);
    List<Product> findByStatus(STATUS status);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByLoaiSanPham(TYPE type);
    List<Product> findByQuantityLessThan(int threshold);
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
}
