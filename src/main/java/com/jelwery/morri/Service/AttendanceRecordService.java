package com.jelwery.morri.Service;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.AttendanceRecord;
import com.jelwery.morri.Repository.AttendanceRecordRepository;

@Service
public class AttendanceRecordService {
    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;
 
    public AttendanceRecord createAttendanceRecord(AttendanceRecord record) {
        if (record.getCheckIn() != null && record.getCheckOut() != null) {
            calculateWorkingHours(record);
        }
        return attendanceRecordRepository.save(record);
    }

    private void calculateWorkingHours(AttendanceRecord record) {
        Duration duration = Duration.between(record.getCheckIn(), record.getCheckOut());
        record.setWorkingHours(duration.toHours() + (duration.toMinutesPart() / 60.0));
    }
 
    public List<AttendanceRecord> getAllAttendanceRecords() {
        return attendanceRecordRepository.findAll();
    }
 
    public AttendanceRecord getAttendanceRecordById(String id) {
        return attendanceRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found with id: " + id));
    }
 
    public AttendanceRecord updateAttendanceRecord(String id, AttendanceRecord updatedAttendanceRecord) {
        AttendanceRecord existingAttendanceRecord = getAttendanceRecordById(id);
        existingAttendanceRecord.setCheckIn(updatedAttendanceRecord.getCheckIn());
        existingAttendanceRecord.setCheckOut(updatedAttendanceRecord.getCheckOut());
        existingAttendanceRecord.setStatus(updatedAttendanceRecord.getStatus());
        existingAttendanceRecord.setWorkingHours(updatedAttendanceRecord.getWorkingHours());
        existingAttendanceRecord.setNotes(updatedAttendanceRecord.getNotes());
        return attendanceRecordRepository.save(existingAttendanceRecord);
    }
 
    public void deleteAttendanceRecord(String id) {
        AttendanceRecord existingAttendanceRecord = getAttendanceRecordById(id);
        attendanceRecordRepository.delete(existingAttendanceRecord);
    }

}
