package com.jelwery.morri.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.Model.PhieuService;
import com.jelwery.morri.Service.PhieuDichVuService;

@RestController
@RequestMapping("/phieuDichVu") 
public class PhieuServiceController {
     @Autowired
    private PhieuDichVuService phieuDichVuService;

    @GetMapping
    public ResponseEntity<List<PhieuService>> getAllPhieuServices() {
        List<PhieuService> phieuServices = phieuDichVuService.getAllPhieuServices();
        return new ResponseEntity<>(phieuServices, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhieuService> getPhieuServiceById(@PathVariable String id) {
        try {
            PhieuService phieuService = phieuDichVuService.getPhieuServiceById(id);
            return new ResponseEntity<>(phieuService, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<PhieuService> createPhieuService(@RequestBody PhieuService phieuService) {
        try {
            PhieuService createdPhieuService = phieuDichVuService.createPhieuService(phieuService);
            return new ResponseEntity<>(createdPhieuService, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhieuService> updatePhieuService(
            @PathVariable String id,
            @RequestBody PhieuService phieuService) {
        try {
            PhieuService updatedPhieuService = phieuDichVuService.updatePhieuService(id, phieuService);
            return new ResponseEntity<>(updatedPhieuService, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhieuService(@PathVariable String id) {
        try {
            phieuDichVuService.deletePhieuService(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/delivery-status")
    public ResponseEntity<PhieuService> updateDeliveryStatus(
            @PathVariable String id,
            @RequestParam PhieuService.DELIVERYSTATUS status) {
        try {
            PhieuService updatedPhieuService = phieuDichVuService.updateDeliveryStatus(id, status);
            return new ResponseEntity<>(updatedPhieuService, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
