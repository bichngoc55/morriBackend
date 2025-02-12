package com.jelwery.morri.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.Service.DichVuService;
import com.jelwery.morri.Model.Service;
 
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PatchMapping;

@CrossOrigin(origins = "http://localhost:3000")  
@RequestMapping("/service")
@RestController
public class ServiceController {
    @Autowired
    private DichVuService dichVuService;

    @PostMapping("/create")
    public Service createService(@Valid @RequestBody Service service) throws  Exception {
        System.out.println(service);
        return dichVuService.createService(service);
    }
     @GetMapping
    public List<Service> getAllServices() {
        return dichVuService.getAllServices();
    }
 

    @GetMapping("/{id}")
    public Service getServiceById(@PathVariable String id) {
        return dichVuService.getServiceById(id);
    }

    @PutMapping("/{id}")
    public Service updateService(@PathVariable String id,   @RequestBody Map<String, Object> updates) {
        return dichVuService.updateService(id, updates);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable String id) {
        dichVuService.deleteService(id);
        return ResponseEntity.ok().build();
    }

    
}
