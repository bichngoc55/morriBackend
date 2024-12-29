package com.jelwery.morri.Model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
@Document(collection="salary")

@Data
@NoArgsConstructor
public class Salary {
    @Id
    private String id;

    private String salaryCode;
    private String employeeId;
    private String position;

    private SalaryType salaryType;

    private LocalDateTime salaryStartDate;
    private LocalDateTime salaryEndDate;
 
    private Integer workingDays = 22; // khong tinh ngay nghi le thi 31 hoac 30 - 8 
    private Integer productsCompleted;
 
    private Double baseSalary;
    private Double hourlyRate =20.000;
    private Double commissionRate=0.0 ;  

    private List<BonusPenaltyRecord> bonusRecords;
    private Double totalBonus;

    private List<BonusPenaltyRecord> penaltyRecords;
    private Double totalPenalty;

    private Double calculatedBasePay;
    private Double totalSalary;

    @CreatedDate
    private LocalDateTime createdAt;

    public enum SalaryType {
        COMMISSION_BASED,
        HOURLY_BASED,
        DAILY_BASED
    }
    @Data
    public class BonusPenaltyRecord {
        private String reason;
        private Double amount;
    }}
