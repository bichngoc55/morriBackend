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

import com.jelwery.morri.DTO.SalaryDTO;
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
            @RequestParam int month , @RequestBody SalaryDTO salaryDTO) {
        Salary salary = salaryService.calculateMonthlySalary(employeeId, year, month, salaryDTO); 
        return ResponseEntity.ok(salary);
    }
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Salary>> getSalariesByEmployeeId(@PathVariable String employeeId) {
        List<Salary> salaries = salaryService.getSalariesByEmployeeId(employeeId);
        return ResponseEntity.ok(salaries);
    }
    @PutMapping("/{salaryId}")
    public ResponseEntity<Salary> updateSalaryDetails(
            @PathVariable String salaryId,
            @RequestBody SalaryDTO salaryDTO) {
        Salary updatedSalary = salaryService.updateSalaryDetails(salaryId, salaryDTO);
        return ResponseEntity.ok(updatedSalary);
    }
    
    @PutMapping("/{salaryId}/bonus-penalty/{recordId}")
    public ResponseEntity<Salary> updateBonusPenaltyRecord(
            @PathVariable String salaryId,
            @PathVariable String recordId,
            @RequestBody BonusPenaltyRecord record) {
        Salary updatedSalary = salaryService.updateBonusPenaltyRecord(salaryId, recordId, record);
        return ResponseEntity.ok(updatedSalary);
    }
    @PostMapping("/{salaryId}/bonus-penalty")
    public ResponseEntity<Salary> addBonusPenaltyRecord(
            @PathVariable String salaryId,
            @RequestBody BonusPenaltyRecord record) {
        Salary updatedSalary = salaryService.addBonusPenaltyRecord(salaryId, record);
        return ResponseEntity.ok(updatedSalary);
    }
    @DeleteMapping("/{salaryId}/bonus-penalty/{recordId}")
    public ResponseEntity<Salary> deleteBonusPenaltyRecord(
        @PathVariable String salaryId,
        @PathVariable String recordId) {
        Salary updatedSalary = salaryService.deleteBonusPenaltyRecord(salaryId, recordId);
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
