package com.jelwery.morri.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Model.Schedule;
import com.jelwery.morri.Repository.ScheduleRepository;
import com.jelwery.morri.Repository.UserRepository;

@Service
public class ScheduleService {
     @Autowired
    private ScheduleRepository scheduleRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Schedule> getAllSchedules() {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.lookup("users", "employeeId", "_id", "employee"),
            Aggregation.unwind("employee", true)
        );
        
        AggregationResults<Schedule> results = mongoTemplate.aggregate(
            aggregation, "schedule", Schedule.class);
            
        return results.getMappedResults();
    }

    public Schedule getScheduleById(String id) {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("_id").is(id)),
            Aggregation.lookup("users", "employeeId", "_id", "employee"),
            Aggregation.unwind("employee", true)
        );
        
        AggregationResults<Schedule> results = mongoTemplate.aggregate(
            aggregation, "schedule", Schedule.class);
        
        Schedule result = results.getUniqueMappedResult();
        if (result == null) {
            throw new RuntimeException("Schedule not found with id: " + id);
        }
        return result;
    }

    public List<Schedule> getSchedulesByEmployeeId(String employeeId) {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("employeeId").is(employeeId)),
            Aggregation.lookup("users", "employeeId", "_id", "employee"),
            Aggregation.unwind("employee", true)
        );
        
        AggregationResults<Schedule> results = mongoTemplate.aggregate(
            aggregation, "schedule", Schedule.class);
            
        return results.getMappedResults();
    }

    public Schedule createSchedule(Schedule schedule) {
        validateSchedule(schedule);
        Schedule saved = mongoTemplate.save(schedule);
        return getScheduleById(saved.getId());
    }

    public Schedule updateSchedule(String id, Schedule schedule) {
        if (!scheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found with id: " + id);
        }
        schedule.setId(id);
        validateSchedule(schedule);
        Schedule saved = mongoTemplate.save(schedule);
        return getScheduleById(saved.getId());
    }

    public void deleteSchedule(String id) {
        if (!scheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found with id: " + id);
        }
        scheduleRepository.deleteById(id);
    }

    private void validateSchedule(Schedule schedule) {
        if (schedule.getEmployeeId() == null || schedule.getEmployeeId().isEmpty()) {
            throw new IllegalArgumentException("EmployeeId cannot be null or empty");
        }

        if (!userRepository.existsById(schedule.getEmployeeId())) {
            throw new IllegalArgumentException("Invalid employee reference");
        }

        if (schedule.getWorkDate() == null) {
            throw new IllegalArgumentException("Work date cannot be null");
        }

        // if (schedule.getStartTime() == null) {
        //     throw new IllegalArgumentException("Start time cannot be null");
        // }

        // if (schedule.getEndTime() == null) {
        //     throw new IllegalArgumentException("End time cannot be null");
        // }

        if (schedule.getStartTime().isAfter(schedule.getEndTime())) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }
    }

}
