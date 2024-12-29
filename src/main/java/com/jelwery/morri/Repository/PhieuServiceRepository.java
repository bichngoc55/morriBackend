package com.jelwery.morri.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.jelwery.morri.Model.PhieuService;
import com.jelwery.morri.Model.User;

public interface PhieuServiceRepository extends MongoRepository<PhieuService,String>{
    @Query(value = "{ 'nameService': ?0 }")
    PhieuService findByNameService(String nameService);
     
    @Query(value = "{ 'staffLapHoaDon': ?0 }")
    List<PhieuService> findByStaffLapHoaDon(User staffLapHoaDon);
     
    @Query(value = "{ 'staffLamDichVu': ?0 }")
    List<PhieuService> findByStaffLamDichVu(User staffLamDichVu);
     
    @Query(value = "{ 'deliverystatus': ?0 }")
    List<PhieuService> findByDeliverystatus(PhieuService.DELIVERYSTATUS status);
 
}
