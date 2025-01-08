package com.jelwery.morri.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Model.Voucher;
import com.jelwery.morri.Repository.VoucherRepository;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;
 
    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }
 
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    } 

    public Optional<Voucher> getVoucherById(String id) {
        return voucherRepository.findById(id);
    }
 
    public Optional<Voucher> getVoucherByCode(String code) {
        return voucherRepository.findByVoucherCode(code);
    }
 
    public Voucher updateVoucher(String id, Voucher voucherDetails) {
        Optional<Voucher> voucher = voucherRepository.findById(id);
        if (voucher.isPresent()) {
            Voucher existingVoucher = voucher.get();
            if(voucherDetails.getVoucherName() != null)
            existingVoucher.setVoucherName(voucherDetails.getVoucherName());
            if(voucherDetails.getVoucherCode() != null)

            existingVoucher.setVoucherCode(voucherDetails.getVoucherCode());
            if(voucherDetails.getDiscount() != null)

            existingVoucher.setDiscount(voucherDetails.getDiscount());
            if(voucherDetails.getDescription() != null)

            existingVoucher.setDescription(voucherDetails.getDescription());
            if(voucherDetails.getStatus() != null)

            existingVoucher.setStatus(voucherDetails.getStatus());
            if(voucherDetails.getStartDate() != null)

            existingVoucher.setStartDate(voucherDetails.getStartDate());
            if(voucherDetails.getEndDate() != null)

            existingVoucher.setEndDate(voucherDetails.getEndDate());
            return voucherRepository.save(existingVoucher);
        }
        return null;
    }
 
    public void deleteVoucher(String id) {
        voucherRepository.deleteById(id);
    }
 
    public List<Voucher> getActiveVouchers() {
        LocalDateTime now = LocalDateTime.now();
        return voucherRepository.findByEndDateAfterAndStartDateBefore(now, now);
    }
 
    public List<Voucher> getVouchersByStatus(String status) {
        return voucherRepository.findByStatus(status);
    }
}
