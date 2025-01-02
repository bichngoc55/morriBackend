package com.jelwery.morri.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Absence;
import com.jelwery.morri.Model.User;
import com.jelwery.morri.Repository.AbsenceRepository;
import com.jelwery.morri.Repository.UserRepository; 

@Service
public class AbsenceService {
 @Autowired
    private AbsenceRepository absenceRepository;
    @Autowired
    private UserRepository userRepository;
 
    public Absence createAbsence(Absence absence) {
        System.out.println(absence);
        // User user = userRepository.findById(absence.getEmployeeId().toString())
        // .orElseThrow(() -> new RuntimeException("User not found"));
        
        // absence.setEmployeeId(user);
        absence.setDate(LocalDateTime.now());  
        return absenceRepository.save(absence);
    }
 
    public List<Absence> getAllAbsences() {
        //  User user = userRepository.findById(absence.getEmployeeId().toString())
        // .orElseThrow(() -> new RuntimeException("User not found"));
        return absenceRepository.findAll();
    }
 
    public Absence getAbsenceById(String id) {
        return absenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Absence not found with id: " + id));
    }
 
    public Absence updateAbsence(String id, Absence updatedAbsence) {
        Absence existingAbsence = getAbsenceById(id);
        existingAbsence.setReason(updatedAbsence.getReason());
        existingAbsence.setStatus(updatedAbsence.getStatus());
        return absenceRepository.save(existingAbsence);
    }
 
    public void deleteAbsence(String id) {
        Absence existingAbsence = getAbsenceById(id);
        absenceRepository.delete(existingAbsence);
    }
}
