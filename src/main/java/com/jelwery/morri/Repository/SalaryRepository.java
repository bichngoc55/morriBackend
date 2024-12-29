package com.jelwery.morri.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.Salary;

public interface SalaryRepository extends MongoRepository<Salary,String>{

}
