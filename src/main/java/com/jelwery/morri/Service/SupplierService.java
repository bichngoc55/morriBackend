package com.jelwery.morri.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Model.Supplier;
import com.jelwery.morri.Repository.SupplierRespository;
@Service
public class SupplierService {
    @Autowired
    private SupplierRespository supplierRespository;
    

    public Supplier createSupplier(Supplier supplier) throws Exception {
//        if() check qq j do
        return supplierRespository.save(supplier);
    }
    public List<Supplier> getAllSupplier(){
        return supplierRespository.findAll();
        
    }
    public Supplier getSupplierByPhone(String supplierPhone) throws Exception {
        Optional<Supplier> supplierOpt = supplierRespository.findBySupplierPhone(supplierPhone);
        if (supplierOpt.isPresent()) {
            return supplierOpt.get();
        }
        else {
            throw new Exception("Customer with phone " + supplierPhone + " not found");
        }
    }

}
