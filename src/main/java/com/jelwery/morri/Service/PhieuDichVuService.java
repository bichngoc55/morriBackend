package com.jelwery.morri.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;

import com.jelwery.morri.Model.PhieuService; 
import com.jelwery.morri.Repository.PhieuServiceRepository;
import com.jelwery.morri.Repository.ServiceRepository;
import com.jelwery.morri.Repository.UserRepository;

@Service
public class PhieuDichVuService {
    @Autowired
    private PhieuServiceRepository phieuServiceRepository;
      
    
    public List<PhieuService> getAllPhieuServices() {
        return phieuServiceRepository.findAll();
    }
 
    public PhieuService getPhieuServiceById(String id) {
        Optional<PhieuService> phieuService = phieuServiceRepository.findById(id);
        if (phieuService.isPresent()) {
            return phieuService.get();
        } else {
            throw new RuntimeException("PhieuService not found with id: " + id);
        }
    }
 
    public PhieuService createPhieuService(PhieuService phieuService) {
        validatePhieuService(phieuService);
        phieuService.setCreatedAt(LocalDateTime.now());
        return phieuServiceRepository.save(phieuService);
    }
 
    public PhieuService updatePhieuService(String id, PhieuService phieuService) {
        Optional<PhieuService> existingPhieuServiceOpt = phieuServiceRepository.findById(id);
        if (existingPhieuServiceOpt.isPresent()) {
            PhieuService existingPhieuService = existingPhieuServiceOpt.get();
 
            existingPhieuService.setNameService(phieuService.getNameService());
            existingPhieuService.setServicesId(phieuService.getServicesId());
            existingPhieuService.setDescription(phieuService.getDescription());
            existingPhieuService.setStaffLapHoaDonId(phieuService.getStaffLapHoaDonId());
            existingPhieuService.setStaffLamDichVuId(phieuService.getStaffLamDichVuId());
            existingPhieuService.setQuantity(phieuService.getQuantity());
            existingPhieuService.setTotalPrice(phieuService.getTotalPrice());
            existingPhieuService.setDeliveryDate(phieuService.getDeliveryDate());
            existingPhieuService.setDeliverystatus(phieuService.getDeliverystatus());

            return phieuServiceRepository.save(existingPhieuService);
        } else {
            throw new RuntimeException("PhieuService not found with id: " + id);
        }
    }
 
    public void deletePhieuService(String id) {
        Optional<PhieuService> phieuService = phieuServiceRepository.findById(id);
        if (phieuService.isPresent()) {
            phieuServiceRepository.deleteById(id);
        } else {
            throw new RuntimeException("PhieuService not found with id: " + id);
        }
    }
 
    public PhieuService updateDeliveryStatus(String id, PhieuService.DELIVERYSTATUS status) {
        Optional<PhieuService> phieuServiceOpt = phieuServiceRepository.findById(id);
        if (phieuServiceOpt.isPresent()) {
            PhieuService phieuService = phieuServiceOpt.get();
            phieuService.setDeliverystatus(status);
            return phieuServiceRepository.save(phieuService);
        } else {
            throw new RuntimeException("PhieuService not found with id: " + id);
        }
    }
 
    private void validatePhieuService(PhieuService phieuService) {
        if (phieuService.getNameService() == null || phieuService.getNameService().isEmpty()) {
            throw new IllegalArgumentException("Service name is required.");
        }

        if (phieuService.getServicesId() == null || phieuService.getServicesId().isEmpty()) {
            throw new IllegalArgumentException("At least one service is required.");
        }

        if (phieuService.getStaffLapHoaDonId() == null) {
            throw new IllegalArgumentException("Staff who created the invoice is required.");
        }

        if (phieuService.getStaffLamDichVuId() == null) {
            throw new IllegalArgumentException("Staff performing the service is required.");
        }

        if (phieuService.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        if (phieuService.getTotalPrice() == null || phieuService.getTotalPrice() <= 0) {
            throw new IllegalArgumentException("Total price must be greater than zero.");
        }
    }

}
