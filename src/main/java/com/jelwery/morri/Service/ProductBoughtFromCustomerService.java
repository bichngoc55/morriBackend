package com.jelwery.morri.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.ProductBoughtFromCustomer;
import com.jelwery.morri.Repository.ProductBoughtFromCustomerRepository;

@Service
public class ProductBoughtFromCustomerService {
    @Autowired
    private ProductBoughtFromCustomerRepository productRepository;

    public ProductBoughtFromCustomer createProduct(ProductBoughtFromCustomer product) {
        return productRepository.save(product);
    }

    public List<ProductBoughtFromCustomer> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductBoughtFromCustomer getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public ProductBoughtFromCustomer updateProduct(String id, ProductBoughtFromCustomer updatedProduct) {
        ProductBoughtFromCustomer existingProduct = getProductById(id);
        // existingProduct.setName(updatedProduct.getName());
        // existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        // existingProduct.setLoaiSanPham(updatedProduct.getLoaiSanPham());
        // existingProduct.setMaterial(updatedProduct.getMaterial());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(String id) {
        ProductBoughtFromCustomer existingProduct = getProductById(id);
        productRepository.delete(existingProduct);
    }
}
