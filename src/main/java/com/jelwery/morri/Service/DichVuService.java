package com.jelwery.morri.Service;

import com.jelwery.morri.Model.Service;
import com.jelwery.morri.Repository.ServiceRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;


@org.springframework.stereotype.Service
public class DichVuService {
    @Autowired
    private ServiceRepository serviceRepository;

    //create
    public Service createService(Service service) throws Exception {
        // if(service.getId()==null) {
        //     throw new Exception("Id khong duoc null");
        // }

        return serviceRepository.save(service);
    }
    // 
        public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }
    public Service updateService(String id, Service service) {
        Optional<Service> existingService = serviceRepository.findById(id);
        if (existingService.isPresent()) {
            service.setId(id);
            return serviceRepository.save(service);
        }
        throw new RuntimeException("Service not found with id: " + id);
    }

    public void deleteService(String id) {
        if (!serviceRepository.existsById(id)) {
            throw new RuntimeException("Service not found with id: " + id);
        }
        serviceRepository.deleteById(id);
    }

    public Service getServiceById(String id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));
    }

}
