package com.jelwery.morri.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;

import com.jelwery.morri.DTO.PhieuServiceDTO;
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

    // public PhieuService updatePhieuService(String id, PhieuService phieuServiceDetails) {
    //     PhieuService existingPhieuService = getPhieuServiceById(id);
         
    //     if (phieuServiceDetails.getNameService() != null) {
    //         if (!existingPhieuService.getNameService().equals(phieuServiceDetails.getNameService()) &&
    //             phieuServiceRepository.existsByNameService(phieuServiceDetails.getNameService())) {
    //             throw new DuplicateResourceException("PhieuService with name " + phieuServiceDetails.getNameService() + " already exists");
    //         }
    //         existingPhieuService.setNameService(phieuServiceDetails.getNameService());
    //     }
        
    //     // if (phieuServiceDetails.getServicesId() != null) {
    //     //     existingPhieuService.setServicesId(phieuServiceDetails.getServicesId());
    //     // }
        
    //     if (phieuServiceDetails.getDescription() != null) {
    //         existingPhieuService.setDescription(phieuServiceDetails.getDescription());
    //     }
        
    //     if (phieuServiceDetails.getStaffLapHoaDon() != null) {
    //         existingPhieuService.setStaffLapHoaDon(phieuServiceDetails.getStaffLapHoaDon());
    //     }
        
    //     if (phieuServiceDetails.getStaffLamDichVu() != null) {
    //         existingPhieuService.setStaffLamDichVu(phieuServiceDetails.getStaffLamDichVu());
    //     }
        
    //     if (phieuServiceDetails.getQuantity() > 0) {
    //         existingPhieuService.setQuantity(phieuServiceDetails.getQuantity());
    //     }
        
    //     if (phieuServiceDetails.getTotalPrice() != null) {
    //         existingPhieuService.setTotalPrice(phieuServiceDetails.getTotalPrice());
    //     }
        
    //     if (phieuServiceDetails.getPhieuServiceStatus() != null) {
    //         existingPhieuService.setPhieuServiceStatus(phieuServiceDetails.getPhieuServiceStatus());

    //      }
        
        
        
    //     return phieuServiceRepository.save(existingPhieuService);
    // }
  public PhieuService updatePhieuService(String id, PhieuServiceDTO dto) {
        PhieuService existingPhieuService = getPhieuServiceById(id);
        
        if (dto.getNameService() != null) {
            if (!existingPhieuService.getNameService().equals(dto.getNameService()) &&
                phieuServiceRepository.existsByNameService(dto.getNameService())) {
                throw new DuplicateResourceException("PhieuService with name " + dto.getNameService() + " already exists");
            }
            existingPhieuService.setNameService(dto.getNameService());
        }

        if (dto.getCustomerName() != null) {
            existingPhieuService.setCustomerName(dto.getCustomerName());
        }

        if (dto.getCustomerPhone() != null) {
            existingPhieuService.setCustomerPhone(dto.getCustomerPhone());
        }

        if (dto.getCustomerGender() != null) {
            existingPhieuService.setCustomerGender(dto.getCustomerGender());
        }

       
 
        if (dto.getDescription() != null) {
            existingPhieuService.setDescription(dto.getDescription());
        }

        // if (dto.getStaffLapHoaDonId() != null) {
        //     User staffLapHoaDon = userRepository.findById(dto.getStaffLapHoaDonId())
        //         .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getStaffLapHoaDonId()));
        //     existingPhieuService.setStaffLapHoaDon(staffLapHoaDon);
        // }

        // if (dto.getStaffLamDichVuId() != null) {
        //     User staffLamDichVu = userRepository.findById(dto.getStaffLamDichVuId())
        //         .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getStaffLamDichVuId()));
        //     existingPhieuService.setStaffLamDichVu(staffLamDichVu);
        // }

        if (dto.getQuantity() != null && dto.getQuantity() > 0) {
            existingPhieuService.setQuantity(dto.getQuantity());
        }

        if (dto.getTotalPrice() != null) {
            existingPhieuService.setTotalPrice(dto.getTotalPrice());
        }

        if (dto.getPhieuServiceStatus() != null) {
            existingPhieuService.setPhieuServiceStatus(dto.getPhieuServiceStatus());
        }

        return phieuServiceRepository.save(existingPhieuService);
    }
    public void deletePhieuService(String id) {
        PhieuService phieuService = getPhieuServiceById(id);
        phieuServiceRepository.delete(phieuService);
    }
 
 

}
