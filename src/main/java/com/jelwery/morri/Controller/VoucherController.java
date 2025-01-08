package com.jelwery.morri.Controller;

import java.util.List;
import java.util.Optional;

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

import com.jelwery.morri.Model.Voucher;
import com.jelwery.morri.Service.VoucherService;

@RestController
@RequestMapping("/voucher")
public class VoucherController {  

    @Autowired
    private VoucherService voucherService;
 
    @PostMapping
    public ResponseEntity<Voucher> createVoucher(@RequestBody Voucher voucher) {
        Voucher newVoucher = voucherService.createVoucher(voucher);
        return ResponseEntity.ok(newVoucher);
    }
 
    @GetMapping
    public ResponseEntity<List<Voucher>> getAllVouchers() {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        return ResponseEntity.ok(vouchers);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Voucher> getVoucherById(@PathVariable String id) {
        Optional<Voucher> voucher = voucherService.getVoucherById(id);
        return voucher.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }
 
    @GetMapping("/code/{code}")
    public ResponseEntity<Voucher> getVoucherByCode(@PathVariable String code) {
        Optional<Voucher> voucher = voucherService.getVoucherByCode(code);
        return voucher.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<Voucher> updateVoucher(@PathVariable String id, @RequestBody Voucher voucherDetails) {
        Voucher updatedVoucher = voucherService.updateVoucher(id, voucherDetails);
        if (updatedVoucher != null) {
            return ResponseEntity.ok(updatedVoucher);
        }
        return ResponseEntity.notFound().build();
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable String id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.ok().build();
    }
 
    @GetMapping("/active")
    public ResponseEntity<List<Voucher>> getActiveVouchers() {
        List<Voucher> activeVouchers = voucherService.getActiveVouchers();
        return ResponseEntity.ok(activeVouchers);
    }
 
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Voucher>> getVouchersByStatus(@PathVariable String status) {
        List<Voucher> vouchers = voucherService.getVouchersByStatus(status);
        return ResponseEntity.ok(vouchers);
    }

}
