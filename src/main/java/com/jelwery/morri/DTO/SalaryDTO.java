package com.jelwery.morri.DTO;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SalaryDTO {

    private LocalDateTime salaryReceiveDate; 
    private Double baseSalary;
    private Double hourlyRate =20.000;
    private Double commissionRate=0.0 ;
}
