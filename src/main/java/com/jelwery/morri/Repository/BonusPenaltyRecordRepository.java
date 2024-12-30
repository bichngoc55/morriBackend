package com.jelwery.morri.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.BonusPenaltyRecord;

public interface BonusPenaltyRecordRepository extends MongoRepository<BonusPenaltyRecord,String> {


}
