package com.jelwery.morri.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Model.Attendance;
import com.jelwery.morri.Model.AttendanceRecord;
import com.jelwery.morri.Model.Schedule;
import com.jelwery.morri.Repository.AttendanceRepository;
import com.jelwery.morri.Repository.ScheduleRepository;
import com.jelwery.morri.Repository.UserRepository;

@Service
public class AttendanceService {
@Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MongoTemplate mongoTemplate;
     @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Attendance> getAllAttendances() {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.lookup("users", "employeeId", "_id", "employee"),
            Aggregation.unwind("employee", true)
        );
        
        AggregationResults<Attendance> results = mongoTemplate.aggregate(
            aggregation, "attendance", Attendance.class);
            
        return results.getMappedResults();
    }

    public Attendance getAttendanceById(String id) {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("_id").is(id)),
            Aggregation.lookup("users", "employeeId", "_id", "employee"),
            Aggregation.unwind("employee", true)
        );
        
        AggregationResults<Attendance> results = mongoTemplate.aggregate(
            aggregation, "attendance", Attendance.class);
        
        Attendance result = results.getUniqueMappedResult();
        if (result == null) {
            throw new RuntimeException("Attendance not found with id: " + id);
        }
        return result;
    }

    public List<Attendance> getAttendancesByEmployeeId(String employeeId) {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("employeeId").is(employeeId)),
            Aggregation.lookup("users", "employeeId", "_id", "employee"),
            Aggregation.unwind("employee", true)
        );
        
        AggregationResults<Attendance> results = mongoTemplate.aggregate(
            aggregation, "attendance", Attendance.class);
            
        return results.getMappedResults();
    }

    public Attendance createAttendance(Attendance attendance) {
        System.out.println("createAttendance");
        validateAttendance(attendance); 

        validateAttendanceAgainstSchedule(attendance); 

        calculateWorkingHours(attendance);
        calculateWorkingDays(attendance); 
        Attendance saved = mongoTemplate.save(attendance);
        return getAttendanceById(saved.getId());
    }

    public Attendance updateAttendance(String id, Attendance attendance) {
        if (!attendanceRepository.existsById(id)) {
            throw new RuntimeException("Attendance not found with id: " + id);
        }
        attendance.setId(id);
        validateAttendance(attendance);
        validateAttendanceAgainstSchedule(attendance);
        calculateWorkingHours(attendance);
        calculateWorkingDays(attendance); 
        Attendance saved = mongoTemplate.save(attendance);
        return getAttendanceById(saved.getId());
    }

    public void deleteAttendance(String id) {
        if (!attendanceRepository.existsById(id)) {
            throw new RuntimeException("Attendance not found with id: " + id);
        }
        attendanceRepository.deleteById(id);
    }
    private void validateAttendanceAgainstSchedule(Attendance attendance) {
        for (AttendanceRecord record : attendance.getAttendanceRecords()) {
            if (record.getCheckIn() != null) { 
                Schedule schedule = scheduleRepository.findByEmployeeIdAndWorkDate(
                    attendance.getEmployeeId(), 
                    record.getDate().toLocalDate()
                );
                
                if (schedule == null) {
                    throw new IllegalStateException(
                        String.format("No work schedule found for employee %s on date: %s", 
                            attendance.getEmployeeId(), record.getDate().toLocalDate())
                    );
                }
 
                LocalDateTime scheduleStart = LocalDateTime.of(
                    record.getDate().toLocalDate(), 
                    schedule.getStartTime()
                );
                LocalDateTime scheduleEnd = LocalDateTime.of(
                    record.getDate().toLocalDate(), 
                    schedule.getEndTime()
                ); 
                LocalDateTime earliestAllowedCheckIn = scheduleStart.minusHours(1);
                
                if (record.getCheckIn().isBefore(earliestAllowedCheckIn)) {
                    throw new IllegalStateException(
                        String.format("Check-in too early. Earliest allowed check-in is %s", 
                            earliestAllowedCheckIn)
                    );
                }

                if (record.getCheckIn().isAfter(scheduleEnd)) {
                    throw new IllegalStateException("Check-in after schedule end time is not allowed");
                }
 
                if (record.getCheckOut() != null) { 
                    LocalDateTime latestAllowedCheckOut = scheduleEnd.plusHours(2);

                    if (record.getCheckOut().isBefore(scheduleStart)) {
                        throw new IllegalStateException("Check-out before schedule start time is not allowed");
                    }

                    if (record.getCheckOut().isAfter(latestAllowedCheckOut)) {
                        throw new IllegalStateException(
                            String.format("Check-out too late. Latest allowed check-out is %s", 
                                latestAllowedCheckOut)
                        );
                    }
                }
 
                if (record.getCheckIn().isAfter(scheduleStart)) {
                    record.setStatus("LATE");
                } else {
                    record.setStatus("ON_TIME");
                }
            }
        }
    }
    private void validateAttendance(Attendance attendance) {
        if (attendance.getEmployeeId() == null || attendance.getEmployeeId().isEmpty()) {
            throw new IllegalArgumentException("EmployeeId cannot be null or empty");
        }

        if (!userRepository.existsById(attendance.getEmployeeId())) {
            throw new IllegalArgumentException("Invalid employee reference");
        }

        if (attendance.getYear() <= 0) {
            throw new IllegalArgumentException("Invalid year");
        }

        if (attendance.getMonth() < 1 || attendance.getMonth() > 12) {
            throw new IllegalArgumentException("Invalid month");
        }

        if (attendance.getAttendanceRecords() == null || attendance.getAttendanceRecords().isEmpty()) {
            throw new IllegalArgumentException("Attendance records cannot be empty");
        }
 
        // if (attendance.getId() == null && 
        //     attendanceRepository.existsByEmployeeIdAndYearAndMonth(
        //         attendance.getEmployeeId(), 
        //         attendance.getYear(), 
        //         attendance.getMonth())) {
        //     throw new IllegalArgumentException("Attendance record already exists for this month and year");
        // }
 
        // for (AttendanceRecord record : attendance.getAttendanceRecords()) {
        //     if (record.getDate() == null) {
        //         throw new IllegalArgumentException("Attendance record date cannot be null");
        //     }

        //     if (record.getCheckIn() == null) {
        //         throw new IllegalArgumentException("Check-in time cannot be null");
        //     }

        //     if (record.getCheckOut() != null && record.getCheckOut().isBefore(record.getCheckIn())) {
        //         throw new IllegalArgumentException("Check-out time cannot be before check-in time");
        //     }
        // }
    }


    private void calculateWorkingHours(Attendance attendance) {
        for (AttendanceRecord record : attendance.getAttendanceRecords()) {
            if (record.getCheckIn() != null && record.getCheckOut() != null) {
                Duration duration = Duration.between(record.getCheckIn(), record.getCheckOut());
                double hours = duration.toMinutes() / 60.0;  
                record.setWorkingHours(Math.round(hours * 100.0) / 100.0); 
            } else {
                record.setWorkingHours(0.0);
            }
        }
    }
    private void calculateWorkingDays(Attendance attendance) {
        if (attendance.getAttendanceRecords() == null) {
            attendance.setWorkingDays(0);
            return;
        }

        int workingDays = (int) attendance.getAttendanceRecords().stream()
            .filter(record -> { 
                return record.getCheckIn() != null 
                    && record.getCheckOut() != null 
                    && record.getWorkingHours() != null 
                    && record.getWorkingHours() > 0
                    && (record.getStatus().equals("ON_TIME") 
                        || record.getStatus().equals("LATE"));
            })
            .count();

        attendance.setWorkingDays(workingDays);
    }
}
