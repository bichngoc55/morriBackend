package com.jelwery.morri.DTO;

import com.jelwery.morri.Model.Product;

import lombok.Data;
@Data
public class InventoryReport {
      private Product product;
    private int soLuongTonDau;
    private int soLuongNhap;
    private int soLuongBan;
    private int soLuongTonCuoi;

    public InventoryReport(Product product, int soLuongTonDau, int soLuongNhap, int soLuongBan, int soLuongTonCuoi) {
        this.product = product;
        this.soLuongTonDau = soLuongTonDau;
        this.soLuongNhap = soLuongNhap;
        this.soLuongBan = soLuongBan;
        this.soLuongTonCuoi = soLuongTonCuoi;
    }
}
