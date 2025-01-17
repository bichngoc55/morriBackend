package com.jelwery.morri.Service;

import com.jelwery.morri.Model.Service;
import com.jelwery.morri.Repository.ServiceRepository;

import java.util.List;
import java.util.Map;
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
    public Service updateService(String id, Map<String, Object> updates) {
        Optional<Service> existingService = serviceRepository.findById(id);
        
        if (existingService.isPresent()) {
            Service service = existingService.get();
 
            updates.forEach((key, value) -> {
                switch (key) {
                    case "serviceName":
                        service.setServiceName((String) value);
                        break;
                    case "serviceDescription":
                        service.setServiceDescription((String) value);
                        break;
                    case "serviceUrl":
                        service.setServiceUrl((String) value);
                        break;
                        case "price": 
                        if (value instanceof Integer) {
                            service.setPrice(((Integer) value).doubleValue());
                        } else if (value instanceof Double) {
                            service.setPrice((Double) value);
                        }
                        break;
                    // Có thể thêm các trường khác nếu cần thiết
                }
            });

            // Lưu lại dịch vụ đã được cập nhật
            return serviceRepository.save(service);
        } else {
            throw new RuntimeException("Service not found with id: " + id);
        }
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
