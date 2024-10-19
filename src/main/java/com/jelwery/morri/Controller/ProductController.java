package com.jelwery.morri.Controller;

import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jewelry")
public class ProductController {
    @Autowired
    private ProductService productService;

    //create
    @PostMapping("/create")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }
    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
