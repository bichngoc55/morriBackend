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

    @GetMapping
    public List<Salary> getAllSalaries() {
        return salaryService.getAllSalaries();
    }

    @GetMapping("/{id}")
    public Salary getSalaryById(@PathVariable String id) {
        return salaryService.getSalaryById(id);
    }

    // @GetMapping("/employee/{employeeId}")
    // public List<Salary> getSalariesByEmployeeId(@PathVariable String employeeId) {
    //     return salaryService.getSalariesByEmployeeId(employeeId);
    // }

    @PostMapping
    public Salary createSalary(@RequestBody Salary salary) {
        return salaryService.createSalary(salary);
    }

    @PutMapping("/{id}")
    public Salary updateSalary(@PathVariable String id, @RequestBody Salary salaryDetails) {
        return salaryService.updateSalary(id, salaryDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSalary(@PathVariable String id) {
        salaryService.deleteSalary(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/bonus-penalty")
    public Salary addBonusPenaltyRecord(
            @PathVariable String id,
            @RequestBody BonusPenaltyRecord record) {
        return salaryService.addBonusPenaltyRecord(id, record);
    }
}
