package com.jelwery.morri.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.Attendance;
import com.jelwery.morri.Model.User;

public interface AttendanceRepository extends MongoRepository<Attendance,String>{ 
    List<Attendance> findByEmployeeId(String employeeId);
    // List<Attendance> findByEmployeeIdAndYearAndMonth(String employeeId, int year, int month);
        // List<Attendance> findByEmployeeId(String employeeId);
    
        Optional<Attendance> findByEmployeeIdAndYearAndMonth(User employeeId, int year, int month);
    }
