package com.jelwery.morri.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Model.STATUS;
import com.jelwery.morri.Model.TYPE;

public interface ProductRepository extends MongoRepository<Product, String> {
     Optional<Product> findByName(String name);
     Optional<Product> findByCode(String code);
    List<Product> findByStatus(STATUS status);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByLoaiSanPham(TYPE type);
    List<Product> findByQuantityLessThan(int threshold);
    List<Product> findByCostPriceBetween(Double minCostPrice, Double maxCostPrice);
    List<Product> findBySellingPriceBetween(Double minSellingPrice, Double maxSellingPrice);
}
