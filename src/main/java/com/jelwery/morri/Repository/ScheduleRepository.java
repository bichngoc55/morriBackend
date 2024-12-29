package com.jelwery.morri.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.Schedule;

public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    //  List<Schedule> findByEmployeeId(String employeeId);
    // List<Schedule> findByWorkDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    // List<Schedule> findByEmployeeIdAndWorkDateBetween(String employeeId, LocalDateTime startDate, LocalDateTime endDate);
    // Schedule findByEmployeeIdAndWorkDate(String employeeId, LocalDate workDate);
    List<Schedule> findByWorkDateBetween(LocalDateTime start, LocalDateTime end);


}
