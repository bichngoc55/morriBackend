package com.jelwery.morri.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.Salary;

public interface SalaryRepository extends MongoRepository<Salary,String>{
    List<Salary> findByEmployeeId(String employeeId);
    Salary findByEmployeeIdAndSalaryReceiveDate(String employeeId, LocalDateTime date);

}
