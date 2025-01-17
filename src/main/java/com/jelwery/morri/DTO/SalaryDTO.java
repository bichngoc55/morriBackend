package com.jelwery.morri.DTO;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SalaryDTO {
    private Double baseSalary;
    private Double commissionRate;
    private LocalDateTime salaryReceiveDate;
}
