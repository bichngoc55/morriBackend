package com.jelwery.morri.DTO;

import java.util.Date;

import com.jelwery.morri.Model.GENDER;

import lombok.Data;

@Data
public class CustomerDTO {
     private String name;
    private GENDER gioiTinh;
    private String phoneNumber;
    private Date dateOfBirth;
    private String email;
    private Date ngayDangKyThanhVien;
    private String password;

}
