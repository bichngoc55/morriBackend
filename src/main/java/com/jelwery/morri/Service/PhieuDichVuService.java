package com.jelwery.morri.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.query.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Model.PhieuService; 
import com.jelwery.morri.Repository.PhieuServiceRepository;
import com.jelwery.morri.Repository.ServiceRepository;
import com.jelwery.morri.Repository.UserRepository;

@Service
public class PhieuDichVuService {
    @Autowired
    private PhieuServiceRepository phieuServiceRepository;
     
      @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    private MongoTemplate mongoTemplate; 
    
     public List<PhieuService> getAllPhieuServices() {
        Aggregation aggregation = Aggregation.newAggregation( 
            Aggregation.lookup("users", "staffLapHoaDonId", "_id", "staffLapHoaDon"), 
            Aggregation.lookup("users", "staffLamDichVuId", "_id", "staffLamDichVu"), 
            LookupOperation.newLookup()
                .from("services")
                .localField("services")
                .foreignField("_id")
                .as("serviceDetails"), 
            Aggregation.unwind("staffLapHoaDon", true),
            Aggregation.unwind("staffLamDichVu", true)
        );
        
        AggregationResults<PhieuService> results = mongoTemplate.aggregate(
            aggregation, "phieuService", PhieuService.class);
            
        return results.getMappedResults();
    }
     
    public PhieuService getPhieuServiceById(String id) {
        Aggregation aggregation = Aggregation.newAggregation( 
            Aggregation.match(Criteria.where("_id").is(id)), 
            Aggregation.lookup("users", "staffLapHoaDonId", "_id", "staffLapHoaDon"), 
            Aggregation.lookup("users", "staffLamDichVuId", "_id", "staffLamDichVu"), 
            LookupOperation.newLookup()
                .from("services")
                .localField("services")
                .foreignField("_id")
                .as("serviceDetails"), 
            Aggregation.unwind("staffLapHoaDon", true),
            Aggregation.unwind("staffLamDichVu", true)
        );
        
        AggregationResults<PhieuService> results = mongoTemplate.aggregate(
            aggregation, "phieuService", PhieuService.class);
        
        PhieuService result = results.getUniqueMappedResult();
        if (result == null) {
            throw new RuntimeException("PhieuService not found with id: " + id);
        }
        return result;
    }
      
    public PhieuService createPhieuService(PhieuService phieuService) {
        System.out.println("Den day r");
        validatePhieuService(phieuService);
        System.out.println("Den day r");

        phieuService.setCreatedAt(LocalDateTime.now());
        System.out.println("Den day r");

        if (phieuService.getDeliverystatus() == null) {
            phieuService.setDeliverystatus(PhieuService.DELIVERYSTATUS.NOT_YET);
        }
        System.out.println("Den day r");
        

        PhieuService saved = mongoTemplate.save(phieuService);
 
        System.out.println("Den day r");

        return getPhieuServiceById(saved.getId());
    }
     
    public PhieuService updatePhieuService(String id, PhieuService phieuService) {
        PhieuService existing = getPhieuServiceById(id);
        
        phieuService.setId(id);
        phieuService.setCreatedAt(existing.getCreatedAt());
        
        validatePhieuService(phieuService);
        PhieuService saved = mongoTemplate.save(phieuService);
        
        return getPhieuServiceById(saved.getId());
    }
     
    public void deletePhieuService(String id) {
        if (!phieuServiceRepository.existsById(id)) {
            throw new RuntimeException("PhieuService not found with id: " + id);
        }
        phieuServiceRepository.deleteById(id);
    }
     
    public PhieuService updateDeliveryStatus(String id, PhieuService.DELIVERYSTATUS status) {
        PhieuService phieuService = getPhieuServiceById(id);
        phieuService.setDeliverystatus(status);
        return mongoTemplate.save(phieuService);
    }
     
    private void validatePhieuService(PhieuService phieuService) {
        if (phieuService.getNameService() == null || phieuService.getNameService().isEmpty()) {
            throw new IllegalArgumentException("NameService cannot be null or empty.");
        }
        
        if (phieuService.getServices() == null || phieuService.getServices().isEmpty()) {
            throw new IllegalArgumentException("Services list cannot be null or empty.");
        }
        
        if (phieuService.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
        
        if (phieuService.getTotalPrice() == null || phieuService.getTotalPrice() <= 0) {
            throw new IllegalArgumentException("TotalPrice must be greater than 0.");
        }
         
        if (phieuService.getStaffLapHoaDonId() != null && 
            !userRepository.existsById(phieuService.getStaffLapHoaDonId())) {
            throw new IllegalArgumentException("Invalid staffLapHoaDon reference.");
        }
        
        if (phieuService.getStaffLamDichVuId() != null && 
            !userRepository.existsById(phieuService.getStaffLamDichVuId())) {
            throw new IllegalArgumentException("Invalid staffLamDichVu reference.");
        }
        
        
        // for (String serviceId : phieuService.getServices()) {
        //     if (!serviceRepository.existsById(serviceId)) {
        //         throw new IllegalArgumentException("Invalid service reference: " + serviceId);
        //     }
        // }
    }

}
