package com.jelwery.morri.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.Model.BonusPenaltyRecord;
import com.jelwery.morri.Service.BonusPenaltyRecordService;
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/bonusPenalty")
public class BonusPenaltyRecordController {
    @Autowired
    private BonusPenaltyRecordService bonusPenaltyRecordService;
 
    @PostMapping
    public ResponseEntity<BonusPenaltyRecord> createBonusPenaltyRecord(@RequestBody BonusPenaltyRecord record) {
        BonusPenaltyRecord createdRecord = bonusPenaltyRecordService.createBonusPenaltyRecord(record);
        return ResponseEntity.ok(createdRecord);
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<BonusPenaltyRecord> updateBonusPenaltyRecord(
            @PathVariable String id, 
            @RequestBody BonusPenaltyRecord updatedRecord) {
        BonusPenaltyRecord record = bonusPenaltyRecordService.updateBonusPenaltyRecord(id, updatedRecord);
        return ResponseEntity.ok(record);
    }
 
    @GetMapping
    public ResponseEntity<List<BonusPenaltyRecord>> getAllBonusPenaltyRecords() {
        List<BonusPenaltyRecord> records = bonusPenaltyRecordService.getAllBonusPenaltyRecords();
        return ResponseEntity.ok(records);
    }

}
