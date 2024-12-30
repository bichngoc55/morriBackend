package com.jelwery.morri.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.Absence;
import com.jelwery.morri.Repository.AbsenceRepository; 

@Service
public class AbsenceService {
 @Autowired
    private AbsenceRepository absenceRepository;
 
    public Absence createAbsence(Absence absence) {
        absence.setDate(LocalDateTime.now());  
        return absenceRepository.save(absence);
    }
 
    public List<Absence> getAllAbsences() {
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
