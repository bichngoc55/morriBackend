package com.jelwery.morri.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.Absence;

public interface AbsenceRepository extends MongoRepository<Absence,String> {
    List<Absence> findByEmployeeId(String employeeId);
    List<Absence> findByDateBetween(LocalDateTime start, LocalDateTime end);
}
