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

import com.jelwery.morri.Model.Absence;
import com.jelwery.morri.Service.AbsenceService;
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/absence")
public class AbsenceController {
    @Autowired
    private AbsenceService absenceService;
 
    @PostMapping("/create")
    public ResponseEntity<Absence> createAbsence(@RequestBody Absence absence) {
        System.out.println(absence);  
        Absence createdAbsence = absenceService.createAbsence(absence);
        return ResponseEntity.ok(createdAbsence);
    }
 
    @GetMapping
    public ResponseEntity<List<Absence>> getAllAbsences() {
        return ResponseEntity.ok(absenceService.getAllAbsences());
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Absence> getAbsenceById(@PathVariable String id) {
        return ResponseEntity.ok(absenceService.getAbsenceById(id));
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<Absence> updateAbsence(@PathVariable String id, @RequestBody Absence updatedAbsence) {
        Absence updated = absenceService.updateAbsence(id, updatedAbsence);
        return ResponseEntity.ok(updated);
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable String id) {
        absenceService.deleteAbsence(id);
        return ResponseEntity.noContent().build();
    }

}
