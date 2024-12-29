package com.jelwery.morri.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List; 

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
     
      @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ServiceRepository serviceRepository; 
    
    public List<PhieuService> getAllPhieuServices() { 
         List<PhieuService> phieuServices = phieuServiceRepository.findAll();
     
        for (PhieuService phieuService : phieuServices) { 
            if (phieuService.getStaffLapHoaDonId() != null) {
                userRepository.findById(phieuService.getStaffLapHoaDonId())
                    .ifPresent(staff -> {
                        phieuService.setStaffLapHoaDonId(staff.getId()); 
                    });
            }
         
        if (phieuService.getStaffLamDichVuId() != null) {
            userRepository.findById(phieuService.getStaffLamDichVuId())
                .ifPresent(staff -> {
                    phieuService.setStaffLamDichVuId(staff.getId()); 
                });
        }
         
        if (phieuService.getServicesId() != null && !phieuService.getServicesId().isEmpty()) {
            List<String> enhancedServices = new ArrayList<>();
            for (String serviceId : phieuService.getServicesId()) {
                serviceRepository.findById(serviceId)
                    .ifPresent(service -> {
                        enhancedServices.add(service.getId()); 
                    });
            }
            phieuService.setServicesId(enhancedServices);
        }
    }
    
    return phieuServices;
    }

    public PhieuService getPhieuServiceById(String id) {
        PhieuService phieuService = phieuServiceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("PhieuService not found with id: " + id));
 
        if (phieuService.getStaffLapHoaDonId() != null && 
            !userRepository.existsById(phieuService.getStaffLapHoaDonId())) {
            throw new RuntimeException("Referenced staffLapHoaDon no longer exists");
        }

        if (phieuService.getStaffLamDichVuId() != null && 
            !userRepository.existsById(phieuService.getStaffLamDichVuId())) {
            throw new RuntimeException("Referenced staffLamDichVu no longer exists");
        }
 
        if (phieuService.getServicesId() != null) {
            for (String serviceId : phieuService.getServicesId()) {
                if (!serviceRepository.existsById(serviceId)) {
                    throw new RuntimeException("Referenced service no longer exists: " + serviceId);
                }
            }
        }

        return phieuService;
    }

    public PhieuService createPhieuService(PhieuService phieuService) {
        validatePhieuService(phieuService); 
        phieuService.setCreatedAt(LocalDateTime.now());
 
        if (phieuService.getDeliverystatus() == null) {
            phieuService.setDeliverystatus(PhieuService.DELIVERYSTATUS.NOT_YET);
        }
 
        if (phieuService.getServicesId() != null && !phieuService.getServicesId().isEmpty()) {
            double totalPrice = 0.0;
            for (String serviceId : phieuService.getServicesId()) {
                com.jelwery.morri.Model.Service service = serviceRepository.findById(serviceId)
                    .orElseThrow(() -> new RuntimeException("Service not found with id: " + serviceId));
                totalPrice += service.getPrice() * phieuService.getQuantity();
            }
            phieuService.setTotalPrice(totalPrice);
        }

        return phieuServiceRepository.save(phieuService);
    }

    public PhieuService updatePhieuService(String id, PhieuService phieuService) {
        PhieuService existing = getPhieuServiceById(id);
         
        phieuService.setId(id);
        phieuService.setCreatedAt(existing.getCreatedAt());
        
        validatePhieuService(phieuService);
 
        if (phieuService.getServicesId() != null && !phieuService.getServicesId().isEmpty()) {
            double totalPrice = 0.0;
            for (String serviceId : phieuService.getServicesId()) {
                com.jelwery.morri.Model.Service service = serviceRepository.findById(serviceId)
                    .orElseThrow(() -> new RuntimeException("Service not found with id: " + serviceId));
                totalPrice += service.getPrice() * phieuService.getQuantity();
            }
            phieuService.setTotalPrice(totalPrice);
        }

        return phieuServiceRepository.save(phieuService);
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
        return phieuServiceRepository.save(phieuService);
    }

    private void validatePhieuService(PhieuService phieuService) {
        if (phieuService.getNameService() == null || phieuService.getNameService().isEmpty()) {
            throw new IllegalArgumentException("NameService cannot be null or empty.");
        }
        
        if (phieuService.getServicesId() == null || phieuService.getServicesId().isEmpty()) {
            throw new IllegalArgumentException("ServicesId list cannot be null or empty.");
        }
        
        if (phieuService.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
         
        if (phieuService.getStaffLapHoaDonId() != null && 
            !userRepository.existsById(phieuService.getStaffLapHoaDonId())) {
            throw new IllegalArgumentException("Invalid staffLapHoaDon reference.");
        }
        
        if (phieuService.getStaffLamDichVuId() != null && 
            !userRepository.existsById(phieuService.getStaffLamDichVuId())) {
            throw new IllegalArgumentException("Invalid staffLamDichVu reference.");
        }
         
        for (String serviceId : phieuService.getServicesId()) {
            if (!serviceRepository.existsById(serviceId)) {
                throw new IllegalArgumentException("Invalid service reference: " + serviceId);
            }
        }
 
        if (phieuService.getDeliveryDate() != null && 
            phieuService.getDeliveryDate().before(java.util.Calendar.getInstance().getTime())) {
            throw new IllegalArgumentException("Delivery date cannot be in the past.");
        }
    }

}
