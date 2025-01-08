package com.jelwery.morri.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.jelwery.morri.Model.TermCondition;
import com.jelwery.morri.Repository.TermConditionRepository;


@org.springframework.stereotype.Service
public class TermConditionService {
    @Autowired
    private TermConditionRepository repository;

    //create
    public TermCondition create(TermCondition termCondition) throws Exception {
     
        return repository.save(termCondition);
    }
    // 
    public List<TermCondition> getAll() {
        return repository.findAll();
    }
    public TermCondition updateService(String id, TermCondition termCondition) {
        Optional<TermCondition> existingTermCondition = repository.findById(id);
        
        if (existingTermCondition.isPresent()) {
            TermCondition updatedTermCondition = existingTermCondition.get();
            
            // Update fields as necessary
            if (termCondition.getDescription() != null)
                updatedTermCondition.setDescription(termCondition.getDescription()); // Update the descriptions

            if (termCondition.getGreeting() != null) 
            updatedTermCondition.setGreeting(termCondition.getGreeting());
            // Add more fields to update if necessary
    
            return repository.save(updatedTermCondition); // Save and return the updated TermCondition
        } else {
            // Handle the case where the TermCondition is not found
            throw new RuntimeException("TermCondition not found with id: " + id);
        }
    }

}
