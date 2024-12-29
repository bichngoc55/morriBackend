package com.jelwery.morri.Service;
 
import java.time.LocalDateTime;
import java.util.ArrayList; 

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Absence;
import com.jelwery.morri.Model.Attendance;
import com.jelwery.morri.Model.AttendanceRecord;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.AbsenceRepository;
import com.jelwery.morri.Repository.AttendanceRepository; 
import com.jelwery.morri.Repository.UserRepository;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private AbsenceRepository absenceRepository;
    @Autowired
    private UserRepository userRepository;

    public Attendance createAttendance(Attendance attendance) {
        String employeeId = attendance.getEmployeeId().getId();
        validateEmployee(employeeId);  
        return attendanceRepository.save(attendance);
    }

    public Attendance recordAttendance(String employeeId, AttendanceRecord record) {
        validateEmployee(employeeId);
        Attendance attendance = getOrCreateAttendance(employeeId, record.getDate());
         
        if (attendance.getAttendanceRecords() == null) {
            attendance.setAttendanceRecords(new ArrayList<>());
        }
        
        attendance.getAttendanceRecords().add(record);
        updateAttendanceTotals(attendance);
        return attendanceRepository.save(attendance);
    }

    public Absence reportAbsence(Absence absence) {
        validateEmployee(absence.getEmployeeId());
        absence.setStatus(Absence.AbsenceStatus.PENDING);
        Absence savedAbsence = absenceRepository.save(absence);

        Attendance attendance = getOrCreateAttendance(absence.getEmployeeId(), absence.getDate()); 
        if (attendance.getAbsences() == null) {
            attendance.setAbsences(new ArrayList<>());
        }
        
        attendance.getAbsences().add(savedAbsence);
        updateAttendanceTotals(attendance);   
        attendanceRepository.save(attendance);
        return savedAbsence;
    }

    private Attendance getOrCreateAttendance(String employeeId, LocalDateTime date) {
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
                newAttendance.setAttendanceRecords(new ArrayList<>());
                newAttendance.setAbsences(new ArrayList<>());
                return attendanceRepository.save(newAttendance);   
            });
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

    private void validateEmployee(String employeeId) {
        if (!userRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee not found with ID: " + employeeId);
        }
    }
    
 
}
