package com.jelwery.morri.Model;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed; 
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
 
@Document(collection="product")
@Data
@NoArgsConstructor
public class Product {
    @Id
    private String id;
    @Indexed(unique=true)
    private String tenSanPham;
    private String moTa;
    private Material chatLieu;
    private Double giaNhap;
    private Date ngayNhap;
    private Double giaBan;
    private String[] hinhAnh;
    private TYPE loaiSanPham;
    private int soLuong;
    private double khoiLuong;
    private STATUS tinhTrang;
    private Double chiPhiPhatSinh;
    // li do tui k code inventoryId o day vi 2 th tro den nhau
}
