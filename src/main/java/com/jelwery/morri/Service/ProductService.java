package com.jelwery.morri.Service;

 import java.util.List;

 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.Exception.DuplicateResourceException; 
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Repository.ProductRepository;
import com.jelwery.morri.Validation.ProductValidation; 

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
        if (productRepository.findByName(product.getTenSanPham()).isPresent()) {
            throw new DuplicateResourceException("Product with name " + product.getTenSanPham() + " already exists");
        }
        return productRepository.save(product);
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    } 
    public Product updateProduct(String productId, Product updatedProduct) {  
        Product existingProduct = productRepository.findById(productId)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
 
        if (updatedProduct.getTenSanPham() != null) { 
            if (!updatedProduct.getTenSanPham().equals(existingProduct.getTenSanPham()) && 
                productRepository.findByName(updatedProduct.getTenSanPham()).isPresent()) {
                throw new DuplicateResourceException("Product with name " + updatedProduct.getTenSanPham() + " already exists");
            }
            existingProduct.setTenSanPham(updatedProduct.getTenSanPham());
        }

        if (updatedProduct.getMoTa() != null) {
            existingProduct.setMoTa(updatedProduct.getMoTa());
        }

        if (updatedProduct.getGiaNhap() != null) {
            existingProduct.setGiaNhap(updatedProduct.getGiaNhap());
        }

        if (updatedProduct.getGiaBan() != null) {
            existingProduct.setGiaBan(updatedProduct.getGiaBan());
        }

        if (updatedProduct.getHinhAnh() != null) {
            existingProduct.setHinhAnh(updatedProduct.getHinhAnh());
        }

        if (updatedProduct.getLoaiSanPham() != null) {
            existingProduct.setLoaiSanPham(updatedProduct.getLoaiSanPham());
        }
        if (updatedProduct.getChatLieu() != null) {
            existingProduct.setChatLieu(updatedProduct.getChatLieu());
        }
 
        if (updatedProduct.getSoLuong() != 0) {   
            existingProduct.setSoLuong(updatedProduct.getSoLuong());
        }

        if (updatedProduct.getKhoiLuong() != 0) {  
            existingProduct.setKhoiLuong(updatedProduct.getKhoiLuong());
        }

        if (updatedProduct.getTinhTrang() != null) {
            existingProduct.setTinhTrang(updatedProduct.getTinhTrang());
        }

        if (updatedProduct.getChiPhiPhatSinh() != null) {
            existingProduct.setChiPhiPhatSinh(updatedProduct.getChiPhiPhatSinh());
        }
 
        // productValidator.validateProduct(existingProduct);
        return productRepository.save(existingProduct);
        } 
}

