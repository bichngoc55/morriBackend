package com.jelwery.morri.Controller;
 
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

    @PostMapping("/record")
    public ResponseEntity<Attendance> recordAttendance(
            @RequestParam String employeeId,
            @RequestBody AttendanceRecord record) {
        return ResponseEntity.ok(attendanceService.recordAttendance(employeeId, record));
    }

    @PostMapping("/absence")
    public ResponseEntity<Absence> reportAbsence(@RequestBody Absence absence) {
        return ResponseEntity.ok(attendanceService.reportAbsence(absence));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Attendance>> getEmployeeAttendance(
            @PathVariable String employeeId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {
        if (year != null && month != null) {
            return ResponseEntity.ok(
                attendanceRepository.findByEmployeeIdAndYearAndMonth(employeeId, year, month));
        }
        return ResponseEntity.ok(attendanceRepository.findByEmployeeId(employeeId));
    }

}
