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
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.Model.Attendance; 
import com.jelwery.morri.Service.AttendanceService; 

@RestController
@RequestMapping("/attendance") 
public class AttendanceController {
     @Autowired
    private AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        return ResponseEntity.ok(attendanceService.getAllAttendances());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable String id) {
        return ResponseEntity.ok(attendanceService.getAttendanceById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Attendance>> getAttendancesByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(attendanceService.getAttendancesByEmployeeId(employeeId));
    }

    @PostMapping
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.createAttendance(attendance));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attendance> updateAttendance(
            @PathVariable String id,
            @RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.updateAttendance(id, attendance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable String id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.ok().build();
    }


}
