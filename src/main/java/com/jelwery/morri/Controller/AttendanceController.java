package com.jelwery.morri.Controller;
 
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController; 
import com.jelwery.morri.Model.Absence;
import com.jelwery.morri.Model.Attendance;
import com.jelwery.morri.Model.AttendanceRecord; 
import com.jelwery.morri.Service.AttendanceService; 
@CrossOrigin(origins = "http://localhost:3000")  

@RestController
@RequestMapping("/attendance") 
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/check-in")
    public ResponseEntity<?> checkIn(@RequestBody AttendanceRecord record) {
        return ResponseEntity.ok(attendanceService.checkIn(record));
    }

    @PostMapping("/check-out")
    public ResponseEntity<?> checkOut(@RequestBody AttendanceRecord record) {
        return ResponseEntity.ok(attendanceService.checkOut(record));
    }

    @GetMapping("/employee/{employeeId}/{year}/{month}")
    public ResponseEntity<Attendance> getMonthlyAttendance(
            @PathVariable String employeeId,
            @PathVariable int year,
            @PathVariable int month) {
        return ResponseEntity.ok(attendanceService.getMonthlyAttendance(employeeId, year, month));
    }

    @PostMapping("/absence")
    public ResponseEntity<?> reportAbsence(@RequestBody Absence absence) {
        return ResponseEntity.ok(attendanceService.reportAbsence(absence));
    }
}
