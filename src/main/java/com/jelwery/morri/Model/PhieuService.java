package com.jelwery.morri.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Document(collection="phieuService")
@NoArgsConstructor
@Data
public class PhieuService {
    @Id
    private String id;
    @Indexed(unique = true)
    private String nameService;
    private ArrayList<Service> services;
    private String description;
    @DBRef
    private User staffLapHoaDon;
    @DBRef
    private User staffLamDichVu;
    private int quantity;
    private Double totalPrice;
    @CreatedDate
    private LocalDateTime createdAt;
    private Date deliveryDate;
    private DELIVERYSTATUS deliverystatus;
    public enum DELIVERYSTATUS{
        COMPLETED,
        NOT_YET
    }



}
