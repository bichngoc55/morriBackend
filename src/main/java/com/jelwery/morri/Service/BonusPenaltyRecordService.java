package com.jelwery.morri.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.BonusPenaltyRecord;
import com.jelwery.morri.Repository.BonusPenaltyRecordRepository;

@Service
public class BonusPenaltyRecordService {
    @Autowired
    private BonusPenaltyRecordRepository bonusPenaltyRecordRepository;
 
    public BonusPenaltyRecord createBonusPenaltyRecord(BonusPenaltyRecord record) {
        return bonusPenaltyRecordRepository.save(record);
    }

    public List<BonusPenaltyRecord> getAllBonusPenaltyRecords() {
        return bonusPenaltyRecordRepository.findAll();
    }
    public BonusPenaltyRecord updateBonusPenaltyRecord(String id, BonusPenaltyRecord updatedRecord) {
        BonusPenaltyRecord existingRecord = bonusPenaltyRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BonusPenaltyRecord not found with id: " + id));
        
        existingRecord.setType(updatedRecord.getType());
        existingRecord.setReason(updatedRecord.getReason());
        existingRecord.setAmount(updatedRecord.getAmount());
        return bonusPenaltyRecordRepository.save(existingRecord);
    }

}
