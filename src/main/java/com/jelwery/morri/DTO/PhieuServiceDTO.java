package com.jelwery.morri.DTO;

import java.util.List;

import com.jelwery.morri.Model.PhieuService;

import lombok.Data;

@Data
public class PhieuServiceDTO {
    private String nameService;
    private String customerName;
    private String customerPhone;
    private String customerGender;
    private String customerId;
    private List<String> serviceIds;
    private String description;
    private String staffLapHoaDonId;
    private String staffLamDichVuId;
    private Integer quantity;
    private Double totalPrice;
    private PhieuService.STATUS phieuServiceStatus;
}
