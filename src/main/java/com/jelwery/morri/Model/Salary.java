package com.jelwery.morri.Model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jelwery.morri.DTO.BonusPenaltyRecordDeserializer;
import com.jelwery.morri.DTO.UserDeserializer;

import lombok.Data;
import lombok.NoArgsConstructor;
@Document(collection="salary")

@Data
@NoArgsConstructor
public class Salary {
    @Id
    private String id; 
    @DocumentReference
    @JsonDeserialize(contentUsing = UserDeserializer.class)
    private String employeeId;  

    private LocalDateTime salaryReceiveDate;
 
    private Integer workingDays = 22; // khong tinh ngay nghi le thi 31 hoac 30 - 8 
    private Integer productsCompleted;
 
    private Double baseSalary;
    private Double hourlyRate =20.000;
    private Double commissionRate=0.0 ;
    @DocumentReference  
    @JsonDeserialize(contentUsing = BonusPenaltyRecordDeserializer.class)
    private List<BonusPenaltyRecord> bonusRecords;
    private Double totalBonusAndPenalty;
    private Double salaryCommissionBased;
    private Double salaryHourlyBased;
    private Double salaryDailyBased;
    private Double calculatedBasePay;
    private Double totalSalary;
    @CreatedDate
    private LocalDateTime createdAt;
 
}
