package com.jelwery.morri.Controller;
 
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PatchExchange;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Absence;
import com.jelwery.morri.Model.Attendance;
import com.jelwery.morri.Model.AttendanceRecord;
import com.jelwery.morri.Repository.AttendanceRepository;
import com.jelwery.morri.Service.AttendanceService; 

@RestController
@RequestMapping("/attendance") 
public class AttendanceController {
     @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private AttendanceRepository attendanceRepository;

     @PostMapping("/create")
    public ResponseEntity<Attendance> createAttendance(
            @RequestParam String employeeId) {
                LocalDateTime localDateTime = LocalDateTime.now();


        try {
            Attendance attendance = attendanceService.getOrCreateAttendance(employeeId, localDateTime);
            return ResponseEntity.ok(attendance);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/record")
    public ResponseEntity<Attendance> recordAttendance(
            @RequestParam String employeeId,
            @RequestBody Attendance record) {
        return ResponseEntity.ok(attendanceService.recordAttendance(employeeId, record));
    }
 

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Attendance>> getEmployeeAttendance(
            @PathVariable String employeeId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {
        // if (year != null && month != null) {
        //     return ResponseEntity.ok(
        //         attendanceRepository.findByEmployeeIdAndYearAndMonth(employeeId, year, month));
        // }
        if (year != null && month != null) {
            return attendanceRepository.findByEmployeeIdAndYearAndMonth(employeeId, year, month)
                    .map(record -> ResponseEntity.ok(List.of(record)))
                    .orElseGet(() -> ResponseEntity.ok(List.of()));
        }
        return ResponseEntity.ok(attendanceRepository.findByEmployeeId(employeeId));
    }
    @PostMapping("/validate")
    public ResponseEntity<?> validateAttendance(
            @RequestParam String employeeId,
            @RequestBody AttendanceRecord record) {
        try {
            AttendanceRecord validatedRecord = 
                attendanceService.validateAttendanceWithSchedule(employeeId, record);
            return ResponseEntity.ok(validatedRecord);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchExchange("/update/{attendanceId}")
    public ResponseEntity<Attendance> updateAttendance(
            @PathVariable String attendanceId,
            @RequestBody Attendance record) {
        try { 
            Attendance existingAttendance = attendanceRepository.findById(attendanceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with ID: " + attendanceId));
 
            existingAttendance.setAttendanceRecords(record.getAttendanceRecords());
            existingAttendance.setAbsences(record.getAbsences());
 
            attendanceService.updateAttendanceRecord(existingAttendance, record); 
            Attendance updatedAttendance = attendanceRepository.save(existingAttendance);
            return ResponseEntity.ok(updatedAttendance);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
