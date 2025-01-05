package com.jelwery.morri.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.Model.BonusPenaltyRecord;
import com.jelwery.morri.Model.Salary;
import com.jelwery.morri.Service.SalaryService;
@CrossOrigin(origins = "http://localhost:3000")  

@RestController
@RequestMapping("/salary")
public class SalaryController {
    @Autowired
    private SalaryService salaryService;
    
    @PostMapping("/calculate/{employeeId}")
    public ResponseEntity<Salary> calculateMonthlySalary(
            @PathVariable String employeeId,
            @RequestParam int year,
            @RequestParam int month , Salary salaryDTO) {
        Salary salary = salaryService.calculateMonthlySalary(employeeId, year, month);
        // them salary DTO nua 
        //  product completed
        return ResponseEntity.ok(salary);
    }
    
    @PostMapping("/{salaryId}/bonus-penalty")
    public ResponseEntity<Salary> addBonusPenaltyRecord(
            @PathVariable String salaryId,
            @RequestBody BonusPenaltyRecord record) {
        Salary updatedSalary = salaryService.addBonusPenaltyRecord(salaryId, record);
        return ResponseEntity.ok(updatedSalary);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSalary(@PathVariable String id) {
        salaryService.deleteSalary(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/")
    public  ResponseEntity<?>  getAllSalary() {
        salaryService.getAllSalaries();
        return ResponseEntity.ok().build();    }
    
  
}
