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

import com.jelwery.morri.Model.AttendanceRecord;
import com.jelwery.morri.Service.AttendanceRecordService;
@CrossOrigin(origins = "http://localhost:3000")  

@RestController
@RequestMapping("/attendance-record")
public class AttendanceRecordController {
 @Autowired
    private AttendanceRecordService attendanceRecordService;
 
    @PostMapping
    public ResponseEntity<AttendanceRecord> createAttendanceRecord(@RequestBody AttendanceRecord attendanceRecord) {
        AttendanceRecord createdAttendanceRecord = attendanceRecordService.createAttendanceRecord(attendanceRecord);
        return ResponseEntity.ok(createdAttendanceRecord);
    }
 
    @GetMapping
    public ResponseEntity<List<AttendanceRecord>> getAllAttendanceRecords() {
        return ResponseEntity.ok(attendanceRecordService.getAllAttendanceRecords());
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceRecord> getAttendanceRecordById(@PathVariable String id) {
        return ResponseEntity.ok(attendanceRecordService.getAttendanceRecordById(id));
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<AttendanceRecord> updateAttendanceRecord(@PathVariable String id, @RequestBody AttendanceRecord updatedAttendanceRecord) {
        AttendanceRecord updated = attendanceRecordService.updateAttendanceRecord(id, updatedAttendanceRecord);
        return ResponseEntity.ok(updated);
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendanceRecord(@PathVariable String id) {
        attendanceRecordService.deleteAttendanceRecord(id);
        return ResponseEntity.noContent().build();
    }
}
