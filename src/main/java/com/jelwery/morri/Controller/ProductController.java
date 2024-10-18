package com.jelwery.morri.Controller;

import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jewlery")
public class ProductController {
    @Autowired
    private ProductService productService;

    //create
    @PostMapping("/create")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }
}
