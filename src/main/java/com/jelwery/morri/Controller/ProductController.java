package com.jelwery.morri.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Service.ProductService;

@CrossOrigin(origins = "*")
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
    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable("productId") String productId) {
        return productService.getProductById(productId);
    }
    @GetMapping("/getProductByCode/{code}")
    public Product getProductByCode(@PathVariable("code") String code) throws Exception {
        return productService.getProductByCode(code);
    }
    @PatchMapping("/update/{productId}")
    public Product updateProduct(@PathVariable("productId") String productId, @RequestBody Product updatedProduct) {
        return productService.updateProduct(productId, updatedProduct);
    }
}
