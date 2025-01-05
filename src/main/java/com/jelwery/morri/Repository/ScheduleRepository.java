package com.jelwery.morri.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.Schedule;


public interface ScheduleRepository extends MongoRepository<Schedule, String> { 
    List<Schedule> findByWorkDateBetween(LocalDateTime start, LocalDateTime end);
    Optional<Schedule> findByWorkDate(LocalDateTime workDate);


}
