package com.jelwery.morri.Controller;

import java.util.List;
import org.springframework.http.HttpStatus;

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

import com.jelwery.morri.Model.BillMua;
import com.jelwery.morri.Service.BillMuaService;
@RestController
@RequestMapping("/billMua")
@CrossOrigin(origins = "http://localhost:3000")  
public class BillMuaController {
 @Autowired
    private BillMuaService billService;

    @PostMapping
    public ResponseEntity<BillMua> createBill(@RequestBody BillMua bill) {
        return new ResponseEntity<>(billService.createBill(bill), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BillMua>> getAllBills() {
        return new ResponseEntity<>(billService.getAllBills(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillMua> getBillById(@PathVariable String id) {
        return new ResponseEntity<>(billService.getBillById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillMua> updateBill(@PathVariable String id, @RequestBody BillMua bill) {
        return new ResponseEntity<>(billService.updateBill(id, bill), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable String id) {
        billService.deleteBill(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
