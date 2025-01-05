package com.jelwery.morri.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.Inventory;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
    public List<Inventory> findByNgayNhapKhoBetween(LocalDateTime start, LocalDateTime end);
}
