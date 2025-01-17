package com.jelwery.morri.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.TermCondition;

public interface TermConditionRepository extends MongoRepository<TermCondition, String> {
    // Additional query methods can be defined here if needed
}