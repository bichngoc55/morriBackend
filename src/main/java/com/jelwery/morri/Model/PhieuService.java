package com.jelwery.morri.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed; 
import org.springframework.data.mongodb.core.mapping.Document; 

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
