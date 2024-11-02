package com.jelwery.morri.Validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.validation.ValidationException;

import com.jelwery.morri.Model.Product;
import com.jelwery.morri.Model.TYPE;

@Component
public class ProductValidation {
    public void validateProduct(Product product) {
        List<String> errors = new ArrayList<>();
        if (!StringUtils.hasText(product.getName())) {
            errors.add("Product name cannot be empty");
        } else if (product.getName().length() > 1000) {
            errors.add("Product name cannot exceed 1000 characters");
        } 
        if (product.getPrice() == null) {
            errors.add("Product price cannot be null");
        } else if (product.getPrice() < 0) {
            errors.add("Product price cannot be negative");
        }
        // Validate quantity
        if (product.getQuantity() < 0) {
            errors.add("Product quantity cannot be negative");
        } 
        if (product.getWeight() <= 0) {
            errors.add("Product weight must be greater than 0");
        } 
        if (product.getLoaiSanPham() == null) {
            errors.add("Product type cannot be null");
        } 
        else {
            try {
                TYPE.valueOf(product.getLoaiSanPham().toString());
            } catch (IllegalArgumentException e) {
                errors.add("Invalid product type");
            }
        }
 
        // if (product.getDescription() != null && product.getDescription().length() > 500) {
        //     errors.add("Product description cannot exceed 500 characters");
        // }
 

        // if (!errors.isEmpty()) {
        //     throw new ValidationException(errors);
        // }
    }
  

}
