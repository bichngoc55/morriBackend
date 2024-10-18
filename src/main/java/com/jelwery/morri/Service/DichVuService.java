package com.jelwery.morri.Service;

import com.jelwery.morri.Model.Service;
import com.jelwery.morri.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;


@org.springframework.stereotype.Service
public class DichVuService {
    @Autowired
    private ServiceRepository serviceRepository;

    //create
    public Service createService(Service service) throws Exception {
        if(service.getId()==null) {
            throw new Exception("Id khong duoc null");
        }

        return serviceRepository.save(service);
    }
}
