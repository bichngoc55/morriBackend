package com.jelwery.morri.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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

    private Date dateOfBirth;
    private ROLE role = ROLE.customer;
    private Date ngayDangKyThanhVien;
    @Indexed(unique = true)
    private String email;
    @Indexed(unique = true)
    private String password;
    @DBRef
    private ArrayList<BillMua> danhSachSanPhamDaMua;
    @DBRef
    private ArrayList<BillBan> danhSachSanPhamDaBan;
}
