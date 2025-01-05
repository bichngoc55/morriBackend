package com.jelwery.morri.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Attendance;
import com.jelwery.morri.Model.BonusPenaltyRecord;
import com.jelwery.morri.Model.Salary;
import com.jelwery.morri.Repository.AttendanceRepository;
import com.jelwery.morri.Repository.SalaryRepository;
import com.jelwery.morri.Repository.UserRepository;

@Service
public class SalaryService {
   @Autowired
    private SalaryRepository salaryRepository;
    
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Salary calculateMonthlySalary(String employeeId, int year, int month) {
        // Get attendance record for the month
        Attendance attendance = attendanceRepository.findByEmployeeIdAndYearAndMonth(
                userRepository.findById(employeeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found")),
                year, month)
            .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));

        // Create new salary record
        Salary salary = new Salary();
        salary.setEmployeeId(employeeId);
        salary.setSalaryReceiveDate(LocalDateTime.now());
        salary.setSalaryCommissionBased(0.0);
        salary.setSalaryHourlyBased(0.0);
        salary.setSalaryDailyBased(0.0);

        // Calculate and set salary for each type
        calculateCommissionBasedSalary(salary, attendance);
        calculateHourlyBasedSalary(salary, attendance);
        calculateDailyBasedSalary(salary, attendance);

        // Sum the calculated salaries
        salary.setTotalSalary(salary.getSalaryCommissionBased() + salary.getSalaryHourlyBased() + salary.getSalaryDailyBased());

        // Calculate bonus and penalties
        calculateBonusAndPenalties(salary);
        
        // Calculate final salary
        salary.setTotalSalary(salary.getTotalSalary() + salary.getTotalBonusAndPenalty());

        return salaryRepository.save(salary);
    }
    
    // Calculate Commission-Based Salary
    private void calculateCommissionBasedSalary(Salary salary, Attendance attendance) {
        if (salary.getCommissionRate() > 0 && salary.getProductsCompleted() != null) {
            double commissionPay = salary.getProductsCompleted() * salary.getCommissionRate();
            salary.setSalaryCommissionBased(salary.getBaseSalary() + commissionPay);
        } else {
            salary.setSalaryCommissionBased(salary.getBaseSalary());
        }
    }
    
    // Calculate Hourly-Based Salary
    private void calculateHourlyBasedSalary(Salary salary, Attendance attendance) {
        if (salary.getHourlyRate() != null && attendance.getTotalWorkingHours() != null) {
            double hourlyPay = attendance.getTotalWorkingHours() * salary.getHourlyRate();
            salary.setSalaryHourlyBased(hourlyPay);
        } else {
            salary.setSalaryHourlyBased(0.0);
        }
    }
    
    // Calculate Daily-Based Salary
    private void calculateDailyBasedSalary(Salary salary, Attendance attendance) {
        if (salary.getBaseSalary() != null && salary.getWorkingDays() != null) {
            int actualWorkingDays = salary.getWorkingDays() - attendance.getTotalAbsences();
            double dailyRate = salary.getBaseSalary() / salary.getWorkingDays();
            salary.setSalaryDailyBased(dailyRate * actualWorkingDays);
        } else {
            salary.setSalaryDailyBased(0.0);
        }
    }
    
    
    private void calculateBonusAndPenalties(Salary salary) {
        if (salary.getBonusRecords() == null || salary.getBonusRecords().isEmpty()) {
            salary.setTotalBonusAndPenalty(0.0);
            return;
        }
        
        double total = salary.getBonusRecords().stream()
            .mapToDouble(record -> {
                if (record.getType() == BonusPenaltyRecord.TYPERECORD.BONUS) {
                    return record.getAmount();
                } else {
                    return -record.getAmount();
                }
            })
            .sum();
            
        salary.setTotalBonusAndPenalty(total);
    }
    
    public Salary addBonusPenaltyRecord(String salaryId, BonusPenaltyRecord record) {
        Salary salary = salaryRepository.findById(salaryId)
            .orElseThrow(() -> new ResourceNotFoundException("Salary record not found"));
            
        salary.getBonusRecords().add(record);
        calculateBonusAndPenalties(salary);
        salary.setTotalSalary(salary.getCalculatedBasePay() + salary.getTotalBonusAndPenalty());
        
        return salaryRepository.save(salary);
    }

    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }

    public Salary getSalaryById(String id) {
        return salaryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Salary not found with id: " + id));
    }
 
    public void deleteSalary(String id) {
        Salary salary = getSalaryById(id);
        salaryRepository.delete(salary);
    }
 
  
 

}
