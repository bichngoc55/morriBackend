package com.jelwery.morri.Model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="phieuService")
@NoArgsConstructor
@Data
public class PhieuService {
    
    @Id
    private String id;
    @Indexed(unique = true)
    private String nameService;
    private List<String> servicesId;
    private String description;
    private String staffLapHoaDonId;   
    private String staffLamDichVuId;  
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
