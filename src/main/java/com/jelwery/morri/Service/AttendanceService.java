package com.jelwery.morri.Service;
 
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Absence;
import com.jelwery.morri.Model.Absence.AbsenceStatus;
import com.jelwery.morri.Model.Attendance;
import com.jelwery.morri.Model.AttendanceRecord;
import com.jelwery.morri.Model.AttendanceRecord.AttendanceStatus;
import com.jelwery.morri.Model.AttendanceRecord.CHECKTYPE;
import com.jelwery.morri.Model.Schedule;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.AbsenceRepository;
import com.jelwery.morri.Repository.AttendanceRecordRepository;
import com.jelwery.morri.Repository.AttendanceRepository;
import com.jelwery.morri.Repository.ScheduleRepository;
import com.jelwery.morri.Repository.UserRepository;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private AbsenceRepository absenceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @Transactional
    public Attendance checkIn(AttendanceRecord record) {
        User employee = userRepository.findById(record.getEmployee().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        
        LocalDateTime now = LocalDateTime.now();
        record.setDate(now);
        record.setCheckIn(now);
        record.setCheckType(CHECKTYPE.IN);
        record.setEmployee(employee);
 
        LocalTime standardStartTime = LocalTime.of(9, 0);
        
        if (now.toLocalTime().isAfter(standardStartTime.plusMinutes(15))) {
            record.setStatus(AttendanceStatus.LATE);
        } else {
            record.setStatus(AttendanceStatus.PRESENT);
        } 
        Attendance attendance = getOrCreateAttendance(employee, now.getYear(), now.getMonthValue());
        
        record = attendanceRecordRepository.save(record);
        attendance.getAttendanceRecords().add(record);
        updateAttendanceStats(attendance);
        
        return attendanceRepository.save(attendance);
    }

    @Transactional
    public Attendance checkOut(AttendanceRecord record) {
        User employee = userRepository.findById(record.getEmployee().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        LocalDateTime now = LocalDateTime.now();
        record.setCheckOut(now);
        record.setCheckType(CHECKTYPE.OUT);
        record.setEmployee(employee);

        Attendance attendance = getOrCreateAttendance(employee, now.getYear(), now.getMonthValue());
         
        AttendanceRecord checkInRecord = attendance.getAttendanceRecords().stream()
                .filter(r -> r.getCheckType() == CHECKTYPE.IN && 
                        r.getDate().toLocalDate().equals(now.toLocalDate()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No check-in record found"));
 
        double hours = ChronoUnit.MINUTES.between(checkInRecord.getCheckIn(), now) / 60.0;
        record.setWorkingHours(hours);

        record = attendanceRecordRepository.save(record);
        attendance.getAttendanceRecords().add(record);
        updateAttendanceStats(attendance);
        
        return attendanceRepository.save(attendance);
    }

    @Transactional
    public Attendance reportAbsence(Absence absence) {
        System.out.println("ehehhe : "+ absence);
        User employee = userRepository.findById(absence.getEmployee().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        absence.setStatus(AbsenceStatus.PENDING);
        absence = absenceRepository.save(absence);

        Attendance attendance = getOrCreateAttendance(employee, 
                absence.getDate().getYear(), 
                absence.getDate().getMonthValue());
        
        attendance.getAbsences().add(absence);
        updateAttendanceStats(attendance);
        return attendanceRepository.save(attendance);
    }

    private Attendance getOrCreateAttendance(User employee, int year, int month) {
        return attendanceRepository.findByEmployeeIdAndYearAndMonth(employee, year, month)
                .orElseGet(() -> {
                    Attendance newAttendance = new Attendance();
                    newAttendance.setEmployeeId(employee);
                    newAttendance.setYear(year);
                    newAttendance.setMonth(month);
                    newAttendance.setAttendanceRecords(new ArrayList<>());
                    newAttendance.setAbsences(new ArrayList<>());
                    newAttendance.setWorkingDays(20);  
                    return attendanceRepository.save(newAttendance);
                });
    }

    private void updateAttendanceStats(Attendance attendance) { 
        attendance.setTotalWorkingHours(
            attendance.getAttendanceRecords().stream()
                .filter(r -> r.getWorkingHours() != null)
                .mapToDouble(AttendanceRecord::getWorkingHours)
                .sum()
        );
 
        attendance.setTotalAbsences(attendance.getAbsences().size());
 
        attendance.setTotalLateArrivals((int) attendance.getAttendanceRecords().stream()
                .filter(r -> r.getStatus() == AttendanceStatus.LATE)
                .count());
    }

    public Attendance getMonthlyAttendance(String employeeId, int year, int month) {
        User employee = userRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        
        return attendanceRepository.findByEmployeeIdAndYearAndMonth(employee, year, month)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));
    }
    @Transactional
public AttendanceRecord updateAttendanceRecord(String recordId, AttendanceRecord updateRequest) {
    AttendanceRecord record = attendanceRecordRepository.findById(recordId)
            .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));
     
    if (updateRequest.getNotes() != null) {
        record.setNotes(updateRequest.getNotes());
    }
    if (updateRequest.getCheckIn() != null) {
        record.setCheckIn(updateRequest.getCheckIn()); 
        LocalTime standardStartTime = LocalTime.of(9, 0);
        if (record.getCheckIn().toLocalTime().isAfter(standardStartTime.plusMinutes(15))) {
            record.setStatus(AttendanceStatus.LATE);
        } else {
            record.setStatus(AttendanceStatus.PRESENT);
        }
    }
    if (updateRequest.getCheckOut() != null) {
        record.setCheckOut(updateRequest.getCheckOut()); 
        if (record.getCheckIn() != null) {
            double hours = ChronoUnit.MINUTES.between(record.getCheckIn(), record.getCheckOut()) / 60.0;
            record.setWorkingHours(hours);
        }
    }

    AttendanceRecord updatedRecord = attendanceRecordRepository.save(record);
     
    Attendance attendance = attendanceRepository.findByEmployeeIdAndYearAndMonth(
            record.getEmployee(),
            record.getDate().getYear(),
            record.getDate().getMonthValue())
            .orElseThrow(() -> new ResourceNotFoundException("Attendance not found"));
    
    updateAttendanceStats(attendance);
    attendanceRepository.save(attendance);
    
    return updatedRecord;
}

@Transactional
public Absence updateAbsence(String absenceId, Absence updateRequest) {
    Absence absence = absenceRepository.findById(absenceId)
            .orElseThrow(() -> new ResourceNotFoundException("Absence record not found"));
     
    if (updateRequest.getReason() != null) {
        absence.setReason(updateRequest.getReason());
    }
    if (updateRequest.getStatus() != null) {
        absence.setStatus(updateRequest.getStatus());
    }
    
    return absenceRepository.save(absence);
}
@Transactional
public void deleteAttendanceRecord(String recordId) {
    AttendanceRecord record = attendanceRecordRepository.findById(recordId)
            .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found")); 
    Attendance attendance = attendanceRepository.findByEmployeeIdAndYearAndMonth(
            record.getEmployee(),
            record.getDate().getYear(),
            record.getDate().getMonthValue())
            .orElseThrow(() -> new ResourceNotFoundException("Attendance not found")); 
    attendance.getAttendanceRecords().removeIf(r -> r.getId().equals(recordId));
     
    updateAttendanceStats(attendance); 
    attendanceRepository.save(attendance);
    attendanceRecordRepository.deleteById(recordId);
}

@Transactional
public void deleteAbsence(String absenceId) {
    Absence absence = absenceRepository.findById(absenceId)
            .orElseThrow(() -> new ResourceNotFoundException("Absence record not found"));
             
    Attendance attendance = attendanceRepository.findByEmployeeIdAndYearAndMonth(
            absence.getEmployee(),
            absence.getDate().getYear(),
            absence.getDate().getMonthValue())
            .orElseThrow(() -> new ResourceNotFoundException("Attendance not found")); 
    attendance.getAbsences().removeIf(a -> a.getId().equals(absenceId)); 
    updateAttendanceStats(attendance); 
    attendanceRepository.save(attendance);
    absenceRepository.deleteById(absenceId);
}
 
}
