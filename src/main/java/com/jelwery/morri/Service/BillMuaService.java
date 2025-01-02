package com.jelwery.morri.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.BillMua;
import com.jelwery.morri.Repository.BillMuaRepository;

@Service
public class BillMuaService {
  @Autowired
    private BillMuaRepository billMuaRepository;

    public BillMua createBill(BillMua bill) { 
        if (bill.getTotalPrice() == null || bill.getTotalPrice() == 0) {
            double total = bill.getDsSanPhamDaMua().stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
            bill.setTotalPrice(total);
        }
        return billMuaRepository.save(bill);
    }

    public List<BillMua> getAllBills() {
        return billMuaRepository.findAll();
    }

    public BillMua getBillById(String id) {
        return billMuaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + id));
    }

    public BillMua updateBill(String id, BillMua updatedBill) {
        BillMua existingBill = getBillById(id);
        
        existingBill.setTotalPrice(updatedBill.getTotalPrice());
        existingBill.setCustomerId(updatedBill.getCustomerId());
        existingBill.setCustomerName(updatedBill.getCustomerName());
        existingBill.setSDT(updatedBill.getSDT());
        existingBill.setCccd(updatedBill.getCccd());
        existingBill.setStatus(updatedBill.getStatus());
        existingBill.setDsSanPhamDaMua(updatedBill.getDsSanPhamDaMua());
        existingBill.setStaffId(updatedBill.getStaffId());
        
        return billMuaRepository.save(existingBill);
    }

    public void deleteBill(String id) {
        BillMua existingBill = getBillById(id);
        billMuaRepository.delete(existingBill);
    }
}
