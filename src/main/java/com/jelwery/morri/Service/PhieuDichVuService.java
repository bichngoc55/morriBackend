package com.jelwery.morri.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;

import com.jelwery.morri.Exception.DuplicateResourceException;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.PhieuService; 
import com.jelwery.morri.Repository.PhieuServiceRepository;
import com.jelwery.morri.Repository.ServiceRepository;
import com.jelwery.morri.Repository.UserRepository;

@Service
public class PhieuDichVuService {
    @Autowired
    private PhieuServiceRepository phieuServiceRepository;
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<PhieuService> getAllPhieuServices() {
        return phieuServiceRepository.findAll();
    }

    public PhieuService getPhieuServiceById(String id) {
        return phieuServiceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("PhieuService not found with id: " + id));
    }

    public PhieuService getPhieuServiceByName(String nameService) {
        return phieuServiceRepository.findByNameService(nameService)
            .orElseThrow(() -> new ResourceNotFoundException("PhieuService not found with name: " + nameService));
    }

    public PhieuService createPhieuService(PhieuService phieuService) { 
        System.out.println("phieu service " + phieuService);
        // if (phieuServiceRepository.existsByNameService(phieuService.getNameService())) {
        //     throw new DuplicateResourceException("PhieuService with name " + phieuService.getNameService() + " already exists");
        // }
         
        // phieuService.getServices().forEach(service -> 
        //     serviceRepository.findById(service.getId())
        //         .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + service.getId()))
        // );
         
        // userRepository.findById(phieuService.getStaffLapHoaDon().getId())
        //     .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + phieuService.getStaffLapHoaDonId().getId()));
        
        // userRepository.findById(phieuService.getStaffLamDichVuId().getId())
        //     .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + phieuService.getStaffLamDichVuId().getId()));
        
        phieuService.setCreatedAt(LocalDateTime.now());
        return phieuServiceRepository.save(phieuService);
    }

    public PhieuService updatePhieuService(String id, PhieuService phieuServiceDetails) {
        PhieuService existingPhieuService = getPhieuServiceById(id);
         
        if (phieuServiceDetails.getNameService() != null) {
            if (!existingPhieuService.getNameService().equals(phieuServiceDetails.getNameService()) &&
                phieuServiceRepository.existsByNameService(phieuServiceDetails.getNameService())) {
                throw new DuplicateResourceException("PhieuService with name " + phieuServiceDetails.getNameService() + " already exists");
            }
            existingPhieuService.setNameService(phieuServiceDetails.getNameService());
        }
        
        // if (phieuServiceDetails.getServicesId() != null) {
        //     existingPhieuService.setServicesId(phieuServiceDetails.getServicesId());
        // }
        
        if (phieuServiceDetails.getDescription() != null) {
            existingPhieuService.setDescription(phieuServiceDetails.getDescription());
        }
        
        // if (phieuServiceDetails.getStaffLapHoaDonId() != null) {
        //     existingPhieuService.setStaffLapHoaDonId(phieuServiceDetails.getStaffLapHoaDonId());
        // }
        
        // if (phieuServiceDetails.getStaffLamDichVuId() != null) {
        //     existingPhieuService.setStaffLamDichVuId(phieuServiceDetails.getStaffLamDichVuId());
        // }
        
        if (phieuServiceDetails.getQuantity() > 0) {
            existingPhieuService.setQuantity(phieuServiceDetails.getQuantity());
        }
        
        if (phieuServiceDetails.getTotalPrice() != null) {
            existingPhieuService.setTotalPrice(phieuServiceDetails.getTotalPrice());
        }
        
        if (phieuServiceDetails.getDeliveryDate() != null) {
            existingPhieuService.setDeliveryDate(phieuServiceDetails.getDeliveryDate());
        }
        
        if (phieuServiceDetails.getDeliverystatus() != null) {
            existingPhieuService.setDeliverystatus(phieuServiceDetails.getDeliverystatus());
        }
        
        return phieuServiceRepository.save(existingPhieuService);
    }

    public void deletePhieuService(String id) {
        PhieuService phieuService = getPhieuServiceById(id);
        phieuServiceRepository.delete(phieuService);
    }
 

    public List<PhieuService> getPhieuServicesByStatus(PhieuService.DELIVERYSTATUS status) {
        return phieuServiceRepository.findByDeliverystatus(status);
    }

}
