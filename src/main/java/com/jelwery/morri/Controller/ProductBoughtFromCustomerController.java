package com.jelwery.morri.Controller;

import java.util.List;

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
import org.springframework.http.HttpStatus;

import com.jelwery.morri.Model.ProductBoughtFromCustomer;
import com.jelwery.morri.Service.ProductBoughtFromCustomerService;
 
@RestController
@RequestMapping("/productMuaLai")
@CrossOrigin(origins = "*")
public class ProductBoughtFromCustomerController {
    @Autowired
    private ProductBoughtFromCustomerService productService;

    @PostMapping
    public ResponseEntity<ProductBoughtFromCustomer> createProduct(@RequestBody ProductBoughtFromCustomer product) {
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductBoughtFromCustomer>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductBoughtFromCustomer> getProductById(@PathVariable String id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductBoughtFromCustomer> updateProduct(@PathVariable String id, 
            @RequestBody ProductBoughtFromCustomer product) {
        return new ResponseEntity<>(productService.updateProduct(id, product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
