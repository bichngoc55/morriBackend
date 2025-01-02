package com.jelwery.morri.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.Model.PhieuService;
import com.jelwery.morri.Service.PhieuDichVuService;

@RestController
@RequestMapping("/phieuDichVu") 
public class PhieuServiceController {
    @Autowired
    private PhieuDichVuService  phieuServiceService;

    @GetMapping
    public List<PhieuService> getAllPhieuServices() {
        return phieuServiceService.getAllPhieuServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhieuService> getPhieuServiceById(@PathVariable String id) {
        PhieuService phieuService = phieuServiceService.getPhieuServiceById(id);
        return ResponseEntity.ok(phieuService);
    }

    @GetMapping("/name/{nameService}")
    public ResponseEntity<PhieuService> getPhieuServiceByName(@PathVariable String nameService) {
        PhieuService phieuService = phieuServiceService.getPhieuServiceByName(nameService);
        return ResponseEntity.ok(phieuService);
    }

    @PostMapping
    public ResponseEntity<PhieuService> createPhieuService(@RequestBody PhieuService phieuService) {
        PhieuService createdPhieuService = phieuServiceService.createPhieuService(phieuService);
        return ResponseEntity.ok(createdPhieuService);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhieuService> updatePhieuService(
            @PathVariable String id,
            @RequestBody PhieuService phieuServiceDetails) {
        PhieuService updatedPhieuService = phieuServiceService.updatePhieuService(id, phieuServiceDetails);
        return ResponseEntity.ok(updatedPhieuService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePhieuService(@PathVariable String id) {
        phieuServiceService.deletePhieuService(id);
        return ResponseEntity.ok().build();
    }

    // @GetMapping("/staff-lap-hoa-don/{staffId}")
    // public List<PhieuService> getPhieuServicesByStaffLapHoaDon(@PathVariable String staffId) {
    //     return phieuServiceService.getPhieuServicesByStaffLapHoaDon(staffId);
    // }

    // @GetMapping("/staff-lam-dich-vu/{staffId}")
    // public List<PhieuService> getPhieuServicesByStaffLamDichVu(@PathVariable String staffId) {
    //     return phieuServiceService.getPhieuServicesByStaffLamDichVu(staffId);
    // }

    @GetMapping("/status/{status}")
    public List<PhieuService> getPhieuServicesByStatus(
            @PathVariable PhieuService.DELIVERYSTATUS status) {
        return phieuServiceService.getPhieuServicesByStatus(status);
    }

}
