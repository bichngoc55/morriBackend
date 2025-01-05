package com.jelwery.morri.Controller;

import com.jelwery.morri.Model.Supplier;
import com.jelwery.morri.Service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping; 

import java.util.List;
@CrossOrigin(origins = "*")


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
    
}
