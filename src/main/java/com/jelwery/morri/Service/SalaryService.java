package com.jelwery.morri.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Attendance;
import com.jelwery.morri.Model.BonusPenaltyRecord;
import com.jelwery.morri.Model.Salary;
import com.jelwery.morri.Repository.AttendanceRepository;
import com.jelwery.morri.Repository.SalaryRepository;

@Service
public class SalaryService {
     @Autowired
    private SalaryRepository salaryRepository;
    
    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }

    public Salary getSalaryById(String id) {
        return salaryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Salary not found with id: " + id));
    }

    // public List<Salary> getSalariesByEmployeeId(String employeeId) {
    //     return salaryRepository.findByEmployeeId(employeeId);
    // }

    public Salary createSalary(Salary salary) { 
        System.out.println("Employee ID: " + salary.getEmployeeId());
    System.out.println("Year: " + salary.getSalaryReceiveDate().getYear());
    System.out.println("Month: " + salary.getSalaryReceiveDate().getMonthValue());

        Attendance attendance = attendanceRepository.findByEmployeeIdAndYearAndMonth(
            salary.getEmployeeId(),
            salary.getSalaryReceiveDate().getYear(),
            salary.getSalaryReceiveDate().getMonthValue()
        ).orElseThrow(() -> new ResourceNotFoundException("Attendance record not found")); 
        salary.setCreatedAt(LocalDateTime.now());

        calculateSalary(salary, attendance);
        return salaryRepository.save(salary);
    }

    public Salary updateSalary(String id, Salary salaryDetails) {
        Salary salary = getSalaryById(id);
        
        salary.setEmployeeId(salaryDetails.getEmployeeId());
        salary.setSalaryType(salaryDetails.getSalaryType());
        salary.setSalaryReceiveDate(salaryDetails.getSalaryReceiveDate());
        salary.setBaseSalary(salaryDetails.getBaseSalary());
        salary.setHourlyRate(salaryDetails.getHourlyRate());
        salary.setCommissionRate(salaryDetails.getCommissionRate());
        salary.setProductsCompleted(salaryDetails.getProductsCompleted());
        salary.setBonusRecords(salaryDetails.getBonusRecords());
         
        Attendance attendance = attendanceRepository.findByEmployeeIdAndYearAndMonth(
            salary.getEmployeeId(),
            salary.getSalaryReceiveDate().getYear(),
            salary.getSalaryReceiveDate().getMonthValue()
        ).orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));
        
        calculateSalary(salary, attendance);
        return salaryRepository.save(salary);
    }

    public void deleteSalary(String id) {
        Salary salary = getSalaryById(id);
        salaryRepository.delete(salary);
    }

    private void calculateSalary(Salary salary, Attendance attendance) { 
        Integer workingDays = attendance.getWorkingDays();
        Double totalWorkingHours = attendance.getTotalWorkingHours();
 
        switch (salary.getSalaryType()) {
            case COMMISSION_BASED:
                salary.setCalculatedBasePay(
                    salary.getBaseSalary() + 
                    (salary.getProductsCompleted() * salary.getCommissionRate())
                );
                break;
                
            case HOURLY_BASED:
                salary.setCalculatedBasePay(
                    salary.getHourlyRate() * totalWorkingHours
                );
                break;
                
            case DAILY_BASED: 
                double dailyRate = salary.getBaseSalary() / 22.0; 
                salary.setCalculatedBasePay(dailyRate * workingDays);
                break;
        }
 
        double totalBonusAndPenalty = 0.0;
        if (salary.getBonusRecords() != null) {
            for (BonusPenaltyRecord record : salary.getBonusRecords()) {
                if (record.getType() == BonusPenaltyRecord.TYPERECORD.BONUS) {
                    totalBonusAndPenalty += record.getAmount();
                } else {
                    totalBonusAndPenalty -= record.getAmount();
                }
            }
        }
         
        double attendancePenalty = calculateAttendancePenalty(attendance, salary.getBaseSalary());
        totalBonusAndPenalty -= attendancePenalty;
        
        salary.setTotalBonusAndPenalty(totalBonusAndPenalty);
        salary.setTotalSalary(salary.getCalculatedBasePay() + totalBonusAndPenalty);
    }
    
    private double calculateAttendancePenalty(Attendance attendance, double baseSalary) {
        double penalty = 0.0;
         
        if (attendance.getTotalLateArrivals() != null && attendance.getTotalLateArrivals() > 0) {
            penalty += (attendance.getTotalLateArrivals() * (baseSalary * 0.001)); // 0.1% per late arrival
        }
         
        if (attendance.getTotalAbsences() != null && attendance.getTotalAbsences() > 0) {
            penalty += (attendance.getTotalAbsences() * (baseSalary * 0.05)); // 5% per absence
        }
        
        return penalty;
    }

    public Salary addBonusPenaltyRecord(String salaryId, BonusPenaltyRecord record) {
        Salary salary = getSalaryById(salaryId);
        if (salary.getBonusRecords() == null) {
            salary.setBonusRecords(new ArrayList<>());
        }
        salary.getBonusRecords().add(record);
         
        Attendance attendance = attendanceRepository.findByEmployeeIdAndYearAndMonth(
            salary.getEmployeeId(),
            salary.getSalaryReceiveDate().getYear(),
            salary.getSalaryReceiveDate().getMonthValue()
        ).orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));
        //         List<Attendance> attendanceList = attendanceRepository.findByEmployeeIdAndYearAndMonth(
        //     salary.getEmployeeId(),
        //     salary.getSalaryReceiveDate().getYear(),
        //     salary.getSalaryReceiveDate().getMonthValue()
        // );

        //     if (attendanceList.isEmpty()) {
        //         throw new ResourceNotFoundException("Attendance record not found");
        //     }

        //     Attendance attendance = attendanceList.get(0); 

        calculateSalary(salary, attendance);
        return salaryRepository.save(salary);
    }

}
