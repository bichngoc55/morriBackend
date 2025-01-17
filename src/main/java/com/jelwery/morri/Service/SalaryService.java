package com.jelwery.morri.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jelwery.morri.DTO.SalaryDTO;
import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Attendance;
import com.jelwery.morri.Model.BonusPenaltyRecord;
import com.jelwery.morri.Model.Salary;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.AttendanceRepository;
import com.jelwery.morri.Repository.BillBanRepository;
import com.jelwery.morri.Repository.BillMuaRepository;
import com.jelwery.morri.Repository.BonusPenaltyRecordRepository;
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
    @Autowired
    private BonusPenaltyRecordRepository bonusPenaltyRecordRepository;

 @Autowired
    private BillBanRepository billBanRepository;  

    @Autowired
    private BillMuaRepository billMuaRepository;  
    @Transactional
    public Salary calculateMonthlySalary(String employeeId, int year, int month, SalaryDTO salaryDTO) {
        // Verify employee exists
       User user= userRepository.findById(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        // Get attendance record
        Attendance attendance = attendanceRepository.findByEmployeeIdAndYearAndMonth(user, year, month)
            .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));

        // Calculate products completed from bills
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);
        
        int productsFromBillBan = billBanRepository.countProductsByEmployeeIdAndYearAndMonth(employeeId, year, month);
        int productsFromBillMua = billMuaRepository.countProductsByEmployeeIdAndYearAndMonth(employeeId, year, month);
        
        int totalProductsCompleted = productsFromBillBan + productsFromBillMua;

        // Create new salary record
        Salary salary = new Salary();
        salary.setEmployeeId(user);
        
        // Set salary receive date (end of month if not provided)
        if (salaryDTO.getSalaryReceiveDate() != null) {
            salary.setSalaryReceiveDate(salaryDTO.getSalaryReceiveDate());
        } else {
            salary.setSalaryReceiveDate(endDate);
        }

        // Set base salary
        if (salaryDTO.getBaseSalary() != null) {
            salary.setBaseSalary(salaryDTO.getBaseSalary());
        } else {
            throw new IllegalArgumentException("Base salary is required");
        }

        // Set commission rate if provided
        if (salaryDTO.getCommissionRate() != null) {
            salary.setCommissionRate(salaryDTO.getCommissionRate());
        }

        salary.setProductsCompleted(totalProductsCompleted);
        
        // Initialize salary components
        salary.setSalaryCommissionBased(0.0);
        salary.setSalaryHourlyBased(0.0);
        salary.setSalaryDailyBased(0.0);

        // Calculate different salary components
        calculateCommissionBasedSalary(salary, attendance);
        calculateHourlyBasedSalary(salary, attendance);
        calculateDailyBasedSalary(salary, attendance);

        // Calculate total base pay
        double calculatedBasePay = salary.getSalaryCommissionBased() + 
                                 salary.getSalaryHourlyBased() + 
                                 salary.getSalaryDailyBased();
        salary.setCalculatedBasePay(calculatedBasePay);

        // Initialize bonus/penalty
        salary.setBonusRecords(new ArrayList<>());
        salary.setTotalBonusAndPenalty(0.0);
        
        // Set final total salary
        salary.setTotalSalary(calculatedBasePay);

        return salaryRepository.save(salary);
    }

    private void calculateCommissionBasedSalary(Salary salary, Attendance attendance) {
        if (salary.getCommissionRate() > 0 && salary.getProductsCompleted() != null) {
            double commissionPay = salary.getProductsCompleted() * salary.getCommissionRate();
            salary.setSalaryCommissionBased(commissionPay);
        } else {
            salary.setSalaryCommissionBased(0.0);
        }
    }
     
    private void calculateHourlyBasedSalary(Salary salary, Attendance attendance) {
        if (salary.getHourlyRate() != null && attendance.getTotalWorkingHours() != null) {
            double hourlyPay = attendance.getTotalWorkingHours() * salary.getHourlyRate();
            salary.setSalaryHourlyBased(hourlyPay);
        } else {
            salary.setSalaryHourlyBased(0.0);
        }
    }

    private void calculateDailyBasedSalary(Salary salary, Attendance attendance) {
        if (salary.getBaseSalary() != null && salary.getWorkingDays() != null) {
            int actualWorkingDays = salary.getWorkingDays() - attendance.getTotalAbsences();
            double dailyRate = salary.getBaseSalary() / salary.getWorkingDays();
            salary.setSalaryDailyBased(dailyRate * actualWorkingDays);
        } else {
            salary.setSalaryDailyBased(0.0);
        }
    }

    public List<Salary> getSalariesByEmployeeId(String employeeId) {
        userRepository.findById(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
            List<Salary> salaries = salaryRepository.findByEmployeeId(employeeId);

        for (Salary salary : salaries) {
            if (salary.getBonusRecords() != null) {
                List<BonusPenaltyRecord> loadedBonusRecords = salary.getBonusRecords().stream()
                    .map(record -> bonusPenaltyRecordRepository.findById(record.getId())
                        .orElse(null))
                    .filter(record -> record != null)
                    .collect(Collectors.toList());
                salary.setBonusRecords(loadedBonusRecords);
            }
        }
        
        return salaries;    }
    
    private void calculateBonusAndPenalties(Salary salary) {
        if (salary.getBonusRecords() == null || salary.getBonusRecords().isEmpty()) {
            salary.setTotalBonusAndPenalty(0.0);
            return;
        }
        double total = salary.getBonusRecords().stream()
        .map(record -> bonusPenaltyRecordRepository.findById(record.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Bonus/Penalty record not found: " + record.getId())))
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
        record.setDate(LocalDateTime.now());

         BonusPenaltyRecord savedRecord = bonusPenaltyRecordRepository.save(record);
         if (salary.getBonusRecords() == null) {
            salary.setBonusRecords(new ArrayList<>());
        }
        
        salary.getBonusRecords().add(savedRecord); 
        calculateBonusAndPenalties(salary);
        salary.setTotalSalary(salary.getCalculatedBasePay() + salary.getTotalBonusAndPenalty());
        
        return salaryRepository.save(salary);
    }
    @Transactional
    public Salary updateSalaryDetails(String salaryId, SalaryDTO salaryDTO) {
        Salary salary = salaryRepository.findById(salaryId)
            .orElseThrow(() -> new ResourceNotFoundException("Salary not found"));
         
        if (salaryDTO.getSalaryReceiveDate() != null) {
            salary.setSalaryReceiveDate(salaryDTO.getSalaryReceiveDate());
        }
        
        if (salaryDTO.getBaseSalary() != null) {
            salary.setBaseSalary(salaryDTO.getBaseSalary());
        }
        
        if (salaryDTO.getCommissionRate() != null) {
            salary.setCommissionRate(salaryDTO.getCommissionRate());
        }
         
        recalculateSalary(salary);
        
        return salaryRepository.save(salary);
    }
    
    @Transactional
    public Salary updateBonusPenaltyRecord(String salaryId, String recordId, BonusPenaltyRecord updatedRecord) {
        Salary salary = salaryRepository.findById(salaryId)
        .orElseThrow(() -> new ResourceNotFoundException("Salary not found"));
         
    bonusPenaltyRecordRepository.findById(recordId)
        .orElseThrow(() -> new ResourceNotFoundException("Bonus/Penalty record not found"));
     
    updatedRecord.setId(recordId); 
    BonusPenaltyRecord savedRecord = bonusPenaltyRecordRepository.save(updatedRecord);
     
    boolean recordFound = false;
    for (int i = 0; i < salary.getBonusRecords().size(); i++) {
        BonusPenaltyRecord record = salary.getBonusRecords().get(i);
        if (record.getId().equals(recordId)) {
            salary.getBonusRecords().set(i, savedRecord);
            recordFound = true;
            break;
        }
    }
    calculateBonusAndPenalties(salary);
    
    if (!recordFound) {
        throw new ResourceNotFoundException("Bonus/Penalty record not found in salary");
    }
    
    recalculateSalary(salary);
    
    return salaryRepository.save(salary);
    }
    
    @Transactional
    public Salary deleteBonusPenaltyRecord(String salaryId, String recordId) {
        Salary salary = salaryRepository.findById(salaryId)
            .orElseThrow(() -> new ResourceNotFoundException("Salary not found"));
         
        boolean recordRemoved = salary.getBonusRecords().removeIf(record -> record.getId().equals(recordId));
        
        if (!recordRemoved) {
            throw new ResourceNotFoundException("Bonus/Penalty record not found");
        }
        bonusPenaltyRecordRepository.deleteById(recordId);
 
        recalculateSalary(salary);
        
        return salaryRepository.save(salary);
    }
    
    private void recalculateSalary(Salary salary) {  

        Attendance attendance = attendanceRepository.findByEmployeeIdAndYearAndMonth(
                salary.getEmployeeId(),
                salary.getSalaryReceiveDate().getYear(),
                salary.getSalaryReceiveDate().getMonthValue())
            .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));
         
        calculateCommissionBasedSalary(salary, attendance);
        calculateHourlyBasedSalary(salary, attendance);
        calculateDailyBasedSalary(salary, attendance);
         
        double calculatedBasePay = salary.getSalaryCommissionBased() + 
                                 salary.getSalaryHourlyBased() + 
                                 salary.getSalaryDailyBased();
        salary.setCalculatedBasePay(calculatedBasePay);
         
        calculateBonusAndPenalties(salary);
         
        salary.setTotalSalary(calculatedBasePay + salary.getTotalBonusAndPenalty());
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
