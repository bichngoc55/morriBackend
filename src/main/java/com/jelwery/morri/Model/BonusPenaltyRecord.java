package com.jelwery.morri.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data; 

@Data
@Document(collection="BonusPenaltyRecord")
@AllArgsConstructor 
public class BonusPenaltyRecord {
    @Id
    private String id;
    private TYPERECORD type;
    private String reason;
    private Double amount;
    public enum TYPERECORD {
        BONUS,
        PENALTY
    }

}
