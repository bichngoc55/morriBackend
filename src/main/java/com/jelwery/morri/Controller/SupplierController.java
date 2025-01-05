package com.jelwery.morri.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jelwery.morri.Service.SupplierService;
import com.jelwery.morri.Model.Supplier;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin; 

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")  


@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/create")
    public Supplier createSupplier (@RequestBody Supplier supplier)  throws Exception {
        return supplierService.createSupplier(supplier);
    }
    @GetMapping
    public List<Supplier> getSupplier() {
        return supplierService.getAllSupplier();
    }
    @GetMapping("/getSupplierByPhone/{supplierPhone}")
    public Supplier getCustomerByPhone(@PathVariable("supplierPhone") String supplierPhone) throws Exception {
        return supplierService.getSupplierByPhone(supplierPhone);
    }
}
