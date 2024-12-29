package com.jelwery.morri.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.Attendance;

public interface AttendanceRepository extends MongoRepository<Attendance,String>{
    // boolean existsByEmployeeIdAndYearAndMonth(String employeeId, int year, int month);
    List<Attendance> findByEmployeeId(String employeeId);
    List<Attendance> findByEmployeeIdAndYearAndMonth(String employeeId, int year, int month);
    
    

}
