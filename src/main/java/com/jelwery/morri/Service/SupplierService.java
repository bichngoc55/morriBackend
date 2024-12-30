package com.jelwery.morri.Service;

import com.jelwery.morri.Model.Supplier;
import com.jelwery.morri.Repository.SupplierRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
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
}
