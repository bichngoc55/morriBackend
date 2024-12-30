package com.jelwery.morri.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.AttendanceRecord;

public interface AttendanceRecordRepository extends MongoRepository<AttendanceRecord,String>{

}
