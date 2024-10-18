package com.jelwery.morri.Service;

import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // create hehe
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

}

