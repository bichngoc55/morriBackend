package com.jelwery.morri.Service;
 
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Absence;
import com.jelwery.morri.Model.Attendance;
import com.jelwery.morri.Model.AttendanceRecord;
import com.jelwery.morri.Model.AttendanceRecord.AttendanceStatus;
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
  
    @Autowired
    private ScheduleRepository scheduleRepository;  

    public AttendanceRecord validateAttendanceWithSchedule(String employeeId, AttendanceRecord record) {
        LocalDateTime recordDate = record.getDate();
        Schedule schedule = scheduleRepository.findByWorkDate(
            recordDate.toLocalDate().atStartOfDay())
            .orElseThrow(() -> new ResourceNotFoundException("No schedule found for date: " + recordDate));

        boolean isInMorningShift = schedule.getMorningShifts().stream()
            .anyMatch(user -> user.getId().equals(employeeId));
        boolean isInAfternoonShift = schedule.getAfternoonShifts().stream()
            .anyMatch(user -> user.getId().equals(employeeId));

        if (!isInMorningShift && !isInAfternoonShift) {
            throw new IllegalStateException("Employee is not scheduled to work on this date");
        }
 
        LocalTime checkInTime = record.getCheckIn().toLocalTime();
        LocalTime checkOutTime = record.getCheckOut().toLocalTime();
         
        LocalTime lateThreshold = schedule.getStartTime().plusMinutes(15);

        if (checkInTime.isAfter(lateThreshold)) {
            record.setStatus(AttendanceStatus.LATE);
        } else {
            record.setStatus(AttendanceStatus.PRESENT);
        }
 
        // double workingHours = calculateWorkingHours(
        //     record.getCheckIn(),
        //     record.getCheckOut(),
        //     schedule.getStartTime(),
        //     schedule.getEndTime()
        // );
        // record.setWorkingHours(workingHours);

        return record;
    }
   public Attendance recordAttendance(String employeeId, Attendance record) { 
        User employee = userRepository.findById(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
         
        Optional<Attendance> existingAttendance = attendanceRepository
            .findByEmployeeIdAndYearAndMonth(employeeId, record.getYear(), record.getMonth());
        
        Attendance attendance;
        if (existingAttendance.isPresent()) { 
            attendance = existingAttendance.get();
            updateAttendanceRecord(attendance, record);
        } else { 
            attendance = new Attendance();
            attendance.setEmployeeId(employee);
            attendance.setYear(record.getYear());
            attendance.setMonth(record.getMonth());
            attendance.setAttendanceRecords(new ArrayList<>());
            attendance.setAbsences(new ArrayList<>());
            updateAttendanceRecord(attendance, record);
        }
        
        return attendanceRepository.save(attendance);
    }
    
    // public void updateAttendanceRecord(Attendance existing, Attendance newRecord) { 
    //     if (newRecord.getAttendanceRecords() != null && !newRecord.getAttendanceRecords().isEmpty()) {
    //         List<AttendanceRecord> validatedRecords = new ArrayList<>();
            
    //         for (AttendanceRecord recordRef : newRecord.getAttendanceRecords()) {
    //             AttendanceRecord fullRecord = attendanceRecordRepository
    //                 .findById(recordRef.getId())
    //                 .orElseThrow(() -> new ResourceNotFoundException(
    //                     "AttendanceRecord not found with ID: " + recordRef.getId()));
    //             validatedRecords.add(fullRecord);
    //         }
             
    //         if (existing.getAttendanceRecords() == null) {
    //             existing.setAttendanceRecords(new ArrayList<>());
    //         }
    //         existing.getAttendanceRecords().addAll(validatedRecords);
    //     } 
    //     if (newRecord.getAbsences() != null && !newRecord.getAbsences().isEmpty()) {
    //         List<Absence> validatedAbsences = new ArrayList<>();
            
    //         for (Absence absenceRef : newRecord.getAbsences()) {
    //             Absence fullAbsence = absenceRepository
    //                 .findById(absenceRef.getId())
    //                 .orElseThrow(() -> new ResourceNotFoundException(
    //                     "Absence not found with ID: " + absenceRef.getId()));
    //             validatedAbsences.add(fullAbsence);
    //         } 
    //         if (existing.getAbsences() == null) {
    //             existing.setAbsences(new ArrayList<>());
    //         }
    //         existing.getAbsences().addAll(validatedAbsences);
    //     } 
    //     updateAttendanceTotals(existing);
    // }
    public void updateAttendanceRecord(Attendance existing, Attendance newRecord) {
        if (newRecord.getAttendanceRecords() != null && !newRecord.getAttendanceRecords().isEmpty()) {
            List<AttendanceRecord> validatedRecords = new ArrayList<>(); 
            for (AttendanceRecord recordRef : newRecord.getAttendanceRecords()) {
                AttendanceRecord fullRecord = attendanceRecordRepository
                    .findById(recordRef.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                        "AttendanceRecord not found with ID: " + recordRef.getId()));
                 
                boolean exists = existing.getAttendanceRecords().stream()
                    .anyMatch(existingRecord -> existingRecord.getId().equals(fullRecord.getId()));
                 
                if (!exists) {
                    validatedRecords.add(fullRecord);
                }
            } 
            if (existing.getAttendanceRecords() == null) {
                existing.setAttendanceRecords(new ArrayList<>());
            }
     
            existing.getAttendanceRecords().addAll(validatedRecords);
        }
    
        if (newRecord.getAbsences() != null && !newRecord.getAbsences().isEmpty()) {
            List<Absence> validatedAbsences = new ArrayList<>(); 
            for (Absence absenceRef : newRecord.getAbsences()) {
                Absence fullAbsence = absenceRepository
                    .findById(absenceRef.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                        "Absence not found with ID: " + absenceRef.getId()));
                validatedAbsences.add(fullAbsence);
            } 
            if (existing.getAbsences() == null) {
                existing.setAbsences(new ArrayList<>());
            }
     
            existing.getAbsences().addAll(validatedAbsences);
        } 
        updateAttendanceTotals(existing);
    }
    
    
    private void updateAttendanceTotals(Attendance attendance) {
        if (attendance.getAttendanceRecords() == null) {
            attendance.setTotalWorkingHours(0.0);
            attendance.setTotalAbsences(0);
            attendance.setTotalLateArrivals(0);
            return;
        }

        double totalHours = attendance.getAttendanceRecords().stream()
            .mapToDouble(AttendanceRecord::getWorkingHours)
            .sum();

        long totalAbsences = attendance.getAttendanceRecords().stream()
            .filter(r -> r.getStatus() == AttendanceRecord.AttendanceStatus.ABSENT)
            .count();

        long totalLate = attendance.getAttendanceRecords().stream()
            .filter(r -> r.getStatus() == AttendanceRecord.AttendanceStatus.LATE)
            .count(); 
        if (attendance.getAbsences() != null) {
            totalAbsences += attendance.getAbsences().size();
        }

        attendance.setTotalWorkingHours(totalHours);
        attendance.setTotalAbsences((int) totalAbsences);
        attendance.setTotalLateArrivals((int) totalLate);
    }

    
   
    public Attendance getOrCreateAttendance(String employeeId, LocalDateTime date) {
        validateEmployee(employeeId);
        int year = date.getYear();
        int month = date.getMonthValue();
        User employee = userRepository.findById(employeeId)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
        
        return attendanceRepository
            .findByEmployeeIdAndYearAndMonth(employeeId, year, month)
            .stream()
            .findFirst()
            .orElseGet(() -> {
                Attendance newAttendance = new Attendance();
                newAttendance.setEmployeeId(employee);
                newAttendance.setYear(year);
                newAttendance.setMonth(month);
                // newAttendance.setAttendanceRecords(new ArrayList<>());
                // newAttendance.setAbsences(new ArrayList<>());
                return attendanceRepository.save(newAttendance);   
            });
    }

    

    private void validateEmployee(String employeeId) {
        if (!userRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee not found with ID: " + employeeId);
        }
    }
    
 
}
