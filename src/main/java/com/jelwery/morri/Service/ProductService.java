package com.jelwery.morri.Service;

 import com.jelwery.morri.Exception.DuplicateResourceException;
 import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Repository.ProductRepository;
import com.jelwery.morri.Validation.ProductValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List; 

@Service
@RestController
@RequestMapping("/jewelry")
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private  ProductValidation productValidator;

    public Product createProduct(Product product) {
        productValidator.validateProduct(product);
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new DuplicateResourceException("Product with name " + product.getName() + " already exists");
        }
        return productRepository.save(product);
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    } 
    public Product updateProduct(String productId, Product updatedProduct) {  
        Product existingProduct = productRepository.findById(productId)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
 
        if (updatedProduct.getName() != null) { 
            if (!updatedProduct.getName().equals(existingProduct.getName()) && 
                productRepository.findByName(updatedProduct.getName()).isPresent()) {
                throw new DuplicateResourceException("Product with name " + updatedProduct.getName() + " already exists");
            }
            existingProduct.setName(updatedProduct.getName());
        }

        if (updatedProduct.getDescription() != null) {
            existingProduct.setDescription(updatedProduct.getDescription());
        }

        if (updatedProduct.getPrice() != null) {
            existingProduct.setPrice(updatedProduct.getPrice());
        }

        if (updatedProduct.getImageUrl() != null) {
            existingProduct.setImageUrl(updatedProduct.getImageUrl());
        }

        if (updatedProduct.getLoaiSanPham() != null) {
            existingProduct.setLoaiSanPham(updatedProduct.getLoaiSanPham());
        }
 
        if (updatedProduct.getQuantity() != 0) {   
            existingProduct.setQuantity(updatedProduct.getQuantity());
        }

        if (updatedProduct.getWeight() != 0) {  
            existingProduct.setWeight(updatedProduct.getWeight());
        }

        if (updatedProduct.getStatus() != null) {
            existingProduct.setStatus(updatedProduct.getStatus());
        }

        if (updatedProduct.getChiPhiPhatSinh() != null) {
            existingProduct.setChiPhiPhatSinh(updatedProduct.getChiPhiPhatSinh());
        }
 
        // productValidator.validateProduct(existingProduct);
        return productRepository.save(existingProduct);
        } 
}

