package com.jelwery.morri.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.jelwery.morri.Model.PhieuService;
import com.jelwery.morri.Model.User;

public interface PhieuServiceRepository extends MongoRepository<PhieuService,String>{
    Optional<PhieuService> findByNameService(String nameService);
    List<PhieuService> findByStaffLapHoaDonId(String staffId);
    List<PhieuService> findByStaffLamDichVuId(String staffId); 
    boolean existsByNameService(String nameService);
 
}
