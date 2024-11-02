package com.jelwery.morri.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed; 
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.Date;
@Document(collection="customer")
@Data
@NoArgsConstructor
public class Customer {
    @Id
    private String Id;

    private String name;

    private GENDER gioiTinh;
    private String phoneNumber;

    private Date dateOfBirth;
    private ROLE role = ROLE.CUSTOMER;
    private Date ngayDangKyThanhVien;
    @Indexed(unique = true)
    private String email;
    @Indexed(unique = true)
    private String password;
    @DocumentReference
    private ArrayList<BillMua> danhSachSanPhamDaMua;
    @DocumentReference
    private ArrayList<BillBan> danhSachSanPhamDaBan;
}
