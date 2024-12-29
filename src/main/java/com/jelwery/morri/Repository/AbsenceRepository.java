package com.jelwery.morri.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.Absence;

public interface AbsenceRepository extends MongoRepository<Absence,String> {

}
